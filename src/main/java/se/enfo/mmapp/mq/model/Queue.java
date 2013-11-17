package se.enfo.mmapp.mq.model;

import java.util.List;

import com.ibm.mq.MQMessage;

public class Queue {

	private final String name;
	private final String qmName;
	private final List<MQMessage> mqMessageList;
	private final int curDepth;
	private final int maxDepth;

	public Queue(final String name, final String qmName, final List<MQMessage> mqMessageList,
			final int curDepth, final int maxDepth) {
		this.name = name;
		this.qmName = qmName;
		this.mqMessageList = mqMessageList;
		this.curDepth = curDepth;
		this.maxDepth = maxDepth;
	}

	public String getName() {
		return name;
	}

	public String getQmName() {
		return qmName;
	}

	public List<MQMessage> getMqMessageList() {
		return mqMessageList;
	}

	public int getCurDepth() {
		return curDepth;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

}
