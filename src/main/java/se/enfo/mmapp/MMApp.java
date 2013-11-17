package se.enfo.mmapp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import se.enfo.mmapp.dao.Dao;
import se.enfo.mmapp.mq.cli.CliWrapper;
import se.enfo.mmapp.mq.model.Queue;
import se.enfo.mmapp.mq.model.QueueManager;

import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;

public class MMApp {

	private static final Logger LOG = Logger.getLogger(MMApp.class);

	public void processQM() {
		final CliWrapper cliWrapper = new CliWrapper();
		final List<QueueManager> qmList = new ArrayList<>();
		qmList.add(new QueueManager("QMGR", 1415, "localhost"));
		qmList.add(new QueueManager("QMGR1", 1416, "localhost"));
		for (final QueueManager queueManager : qmList) {
			final MQQueueManager qm = cliWrapper.getQueueManager(queueManager);
			processQueueManager(cliWrapper, qm);
		}
	}

	private void processQueueManager(final CliWrapper cliWrapper,
			final MQQueueManager qm) {
		final List<String> queueNames = cliWrapper.getNames(qm);
		if (queueNames.isEmpty()) {
			LOG.info("No queue names were found");
		} else {
			for (final String queueName : queueNames) {
				LOG.info("Process queue " + queueName);
				final int options = CMQC.MQOO_INQUIRE | CMQC.MQOO_BROWSE | CMQC.MQOO_INPUT_SHARED;
				final MQQueue queue = cliWrapper.getQueue(qm, queueName, options);
				try {
					if (queue.getCurrentDepth() == 0) {
						LOG.info("Queue is empty");
						break;
					} else {
						processQueue(cliWrapper, qm.getName(), queueName, queue);
					}
				} catch (final MQException e) {
					LOG.error("Failed to get queue depth from queue " + queueName);
				}
			}
		}
	}

	private void processQueue(final CliWrapper cliWrapper, final String qmgrName, final String queueName, final MQQueue queue) {
		final List<MQMessage> messageList = cliWrapper.getMessageList(queue);
		if (messageList.isEmpty()) {
			LOG.info("No messages were found");
		} else {
			final Queue queueEntity = new Queue(queueName, qmgrName, messageList,
					cliWrapper.getQueueDepth(queue),
					cliWrapper.getMaxQueueDepth(queue));
			saveQueueRecord(queueEntity, messageList);
		}
		try {
			queue.close();
		} catch (final MQException e) {
			LOG.error("Failed to close queue " + queueName);
		}
	}

	private void saveQueueRecord(final Queue queueEntity, final List<MQMessage> messageList) {
		final GregorianCalendar firstMessageDate = messageList.get(0).putDateTime;
		final GregorianCalendar lastMessageDate = messageList.get(messageList.size() - 1).putDateTime;
		final Timestamp firstTimestamp = new Timestamp(firstMessageDate.getTimeInMillis());
		final Timestamp lastTimestamp = new Timestamp(lastMessageDate.getTimeInMillis());
		Dao.insertQueue(queueEntity, firstTimestamp, lastTimestamp);
		LOG.info("Queue " + queueEntity.getName() + "on queue manager " + queueEntity.getQmName() +
				" was processed into the database");
	}
}
