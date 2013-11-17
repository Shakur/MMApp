package se.enfo.mmapp.mq.cli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import se.enfo.mmapp.mq.model.QueueManager;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.CMQCFC;
import com.ibm.mq.pcf.PCFMessage;
import com.ibm.mq.pcf.PCFMessageAgent;

public class CliWrapper {

	private static final Logger LOG = Logger.getLogger(CliWrapper.class);

	public MQQueueManager getQueueManager(final QueueManager qm) {
		MQEnvironment.properties.put(CMQC.TRANSPORT_PROPERTY, CMQC.TRANSPORT_MQSERIES_CLIENT);
		MQEnvironment.channel = "JAVA.CHANNEL";
		MQEnvironment.port = qm.getPort();
		MQEnvironment.hostname = qm.getHostname();
		MQQueueManager queueManager = null;
		try {
			queueManager = new MQQueueManager(qm.getName());
			LOG.error("Connected to qmgr " + qm.getName());
		} catch (final MQException e) {
			LOG.error("Failed to connect to qmgr " + qm.getName(), e);
		}
		return queueManager;
	}

	void disconnectFromQm(final MQQueueManager qm) {
		try {
			qm.disconnect();
		} catch (final MQException e) {
			LOG.error("Failed to disconnect", e);
		}
	}

	public List<String> getNames(final MQQueueManager qm) {

		final List<String> queueNames = new ArrayList<String>();;
		final PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_Q);

		request.addParameter(CMQC.MQCA_Q_NAME, "*");
		request.addParameter(CMQC.MQIA_Q_TYPE, CMQC.MQQT_LOCAL);
		request.addFilterParameter(CMQC.MQIA_CURRENT_Q_DEPTH, CMQCFC.MQCFOP_GREATER, 0);

		try {
			LOG.info("Connect to send PCF");
			// Connect a PCFAgent to the specified queue manager
			final PCFMessageAgent agent = new PCFMessageAgent(qm);

			// Use the agent to send the request
			final PCFMessage[] responses = agent.send(request);

			// Check the PCF header (MQCFH) in the first response message
			LOG.info("Successfully got queue list");

			for (int i = 0; i < responses.length; i++) {
				final String queueName = (String)responses[i].getParameterValue (CMQC.MQCA_Q_NAME);
				if (!queueName.contains("SYSTEM.") && !queueName.contains("AMQ.")
						&& !queueName.contains("COM.IBM.MQ.PCF")) {
					queueNames.add(queueName);
					LOG.debug("Queue " + queueName + " was added");
				}
			}

			// default queues we need to check
			queueNames.add("SYSTEM.DEAD.LETTER.QUEUE");
			queueNames.add("SYSTEM.CLUSTER.TRANSMIT.QUEUE");

			// Disconnect
			agent.disconnect();
		} catch (MQException | IOException e) {
			LOG.error("Failed to process queue", e);
		}
		return queueNames;
	}



	public int getQueueDepth(final MQQueue queue) {
		int size = 0;
		try {
			size = queue.getCurrentDepth();
		} catch (final MQException e) {
			LOG.error("Failed to get queue size", e);
		}
		return size;
	}

	public int getMaxQueueDepth(final MQQueue queue) {
		int size = 0;
		try {
			size = queue.getMaximumDepth();
		} catch (final MQException e) {
			LOG.error("Failed to get queue size", e);
		}
		return size;
	}

	public MQQueue getQueue(final MQQueueManager qm, final String queueName, final int options) {
		MQQueue queue = null;
		try {
			queue = qm.accessQueue(queueName, options);
		} catch (final MQException e) {
			try {
				LOG.error("Failed to access queue " + queueName + " on queue manager " + qm.getName());
			} catch (final MQException e1) {
				LOG.error("Failed to get queue manager name", e1);
			}
		}
		return queue;
	}

	public List<MQMessage> getMessageList(final MQQueue queue) {
		final List<MQMessage> messageList = new ArrayList<>();
		MQMessage message = null;
		try {
			while (queue.getCurrentDepth() != 0) {
				try {
					message = getMessage(queue);
				} catch (final MQException e) {
					if (e.reasonCode == 2033) {
						LOG.info("No more messages available");
						break;
					}
				}
				if (message != null) {
					messageList.add(message);
				} else {
					LOG.error("Message is not valid");
				}
			}
		} catch (final MQException e) {
			if (e.getReason() == 2019) {
				LOG.debug("Queue was already closed");
			} else {
				try {
					LOG.error("Failed to process queue " + queue.getName(), e);
				} catch (final MQException e1) {
					LOG.error("Failed to get queue name");
				}
			}
		}
		return messageList;
	}

	private MQMessage getMessage(final MQQueue queue) throws MQException {
		MQMessage message = null;
		message = new MQMessage();
		final MQGetMessageOptions gmo = new MQGetMessageOptions();
		gmo.options = CMQC.MQOO_INPUT_AS_Q_DEF | CMQC.MQGMO_BROWSE_NEXT
				| CMQC.MQGMO_ACCEPT_TRUNCATED_MSG;
		queue.get(message, gmo, 200);
		LOG.debug("Message was browsed");
		return message;
	}
}
