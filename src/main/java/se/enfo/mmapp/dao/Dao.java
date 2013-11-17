package se.enfo.mmapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import se.enfo.mmapp.excel.model.MQMachine;
import se.enfo.mmapp.excel.model.QueueManager;
import se.enfo.mmapp.mq.model.Queue;


public class Dao {

	private static final Logger LOG = Logger.getLogger(Dao.class);

	private static final String INSERT_INTO_MQMACHINES = "INSERT INTO MACHINESANDMQ "
			+ "(NODE, DNS, IP, DESCRIPTION, PROCESSORS, TOTALCORES, PVU, EXTENDEDPVU, OSVERSION, MQVERSION, CANDLEVERSION, COMMENT,"
			+ "DECOMMISSIONED, STATUS, INSTALLED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String INSERT_INTO_QUEUEMANAGERS = "INSERT INTO QUEUEMANAGERS "
			+ "(NAME, SLA, DESCRIPTION, MACHINE, PORT, CLUSTER, CLUSTERROLE, COMMENT, DECOMMISSIONED, VALIDTOYEAR) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	//private static final String INSERT_INTO_QM = "INSERT INTO QM (NAME) VALUES (?)";
	private static final String INSERT_INTO_QUEUE = "INSERT INTO QUEUE "
			+ "(NAME, QM, FIRSTMSGDATE, LASTMSGDATE, QUEUELOAD, CURDEPTH, MAXDEPTH) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)"
			+ "ON DUPLICATE KEY UPDATE "
			+ "FIRSTMSGDATE = ?, LASTMSGDATE = ?, QUEUELOAD = ?, CURDEPTH = ?, MAXDEPTH = ?";
	//private static final String SELECT_FROM_MQMACHINES = "SELECT * FROM MACHINESANDMQ";
	//private static final String SELECT_FROM_QUEUEMANAGERS = "SELECT * FROM QUEUEMANAGERS";

	static Connection getConnection() {
		LOG.debug("Connection to database");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (final ClassNotFoundException e) {
			LOG.error("Failed to find class", e);
		}
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mmapp", "admin", "admin");
			LOG.info("Connection was established");
		} catch (final SQLException e) {
			LOG.error("Connection is not valid", e);
		}
		return connection;
	}

	public static void insertMQMachine(final MQMachine mqMachine) {
		try {
			final PreparedStatement preparedStatement = getConnection().prepareStatement(INSERT_INTO_MQMACHINES);
			preparedStatement.setString(1, mqMachine.getNode());
			preparedStatement.setString(2, mqMachine.getDns());
			preparedStatement.setString(3, mqMachine.getIp());
			preparedStatement.setString(4, mqMachine.getDescription());
			preparedStatement.setInt(5, mqMachine.getProcessors());
			preparedStatement.setInt(6, mqMachine.getTotalCores());
			preparedStatement.setInt(7, mqMachine.getPvu());
			preparedStatement.setInt(8, mqMachine.getExtendedPvu());
			preparedStatement.setString(9, mqMachine.getOsVersion());
			preparedStatement.setString(10, mqMachine.getMqVersion());
			preparedStatement.setString(11, mqMachine.getCandleVersion());
			preparedStatement.setString(12, mqMachine.getComment());
			preparedStatement.setString(13, mqMachine.getDecommisioned());
			preparedStatement.setString(14, mqMachine.getStatus());
			preparedStatement.setString(15, mqMachine.getInstalled());
			preparedStatement.executeUpdate();
			LOG.debug(mqMachine);
		} catch (final SQLException e) {
			LOG.error("Failed to insert into mqmachines table", e);
		}
	}

	public static void insertQueueManager(final QueueManager queueManager) {
		try {
			final PreparedStatement preparedStatement = getConnection()
					.prepareStatement(INSERT_INTO_QUEUEMANAGERS);
			preparedStatement.setString(1, queueManager.getName());
			preparedStatement.setString(2, queueManager.getSla());
			preparedStatement.setString(3, queueManager.getDescription());
			preparedStatement.setString(4, queueManager.getMachine());
			preparedStatement.setInt(5, queueManager.getPort());
			preparedStatement.setString(6, queueManager.getCluster());
			preparedStatement.setString(7, queueManager.getClusterRole());
			preparedStatement.setString(8, queueManager.getComment());
			preparedStatement.setString(9, queueManager.getDecommissioned());
			preparedStatement.setString(10, queueManager.getValidToYear());
			preparedStatement.executeUpdate();
		} catch (final SQLException e) {
			LOG.error("Failed to insert into queuemanagers table", e);
		}
	}

	public static void insertQueue(final Queue queue, final Timestamp firstTimestamp,
			final Timestamp lastTimestamp) {
		try {
			final PreparedStatement preparedStatement = getConnection()
					.prepareStatement(INSERT_INTO_QUEUE);
			preparedStatement.setString(1, queue.getName());
			preparedStatement.setString(2, queue.getQmName());
			preparedStatement.setTimestamp(3, firstTimestamp);
			preparedStatement.setTimestamp(4, lastTimestamp);
			preparedStatement.setDouble(5, queue.getCurDepth() / queue.getMaxDepth() * 100);
			preparedStatement.setInt(6, queue.getCurDepth());
			preparedStatement.setInt(7, queue.getMaxDepth());
			preparedStatement.setTimestamp(8, firstTimestamp);
			preparedStatement.setTimestamp(9, lastTimestamp);
			preparedStatement.setDouble(10, queue.getCurDepth() / queue.getMaxDepth() * 100);
			preparedStatement.setInt(11, queue.getCurDepth());
			preparedStatement.setInt(12, queue.getMaxDepth());
			preparedStatement.executeUpdate();
		} catch (final SQLException e) {
			LOG.error("Failed to insert into queue table", e);
		}
	}

	/*public static List<MQMachine> getMqMachines() {
		List<MQMachine> mqMachinesList = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = getConnection().prepareStatement(SELECT_FROM_MQMACHINES);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String node = rs.getString("node");
				String dns = rs.getString("dns");
				String ip = rs.getString("ip");
				String description = rs.getString("description");
				int processors = rs.getInt("processors");
				int totalcores = rs.getInt("totalcores");
				int pvu = rs.getInt("pvu");
				int extendedpvu = rs.getInt("extendedpvu");
				String osversion = rs.getString("osversion");
				String mqversion = rs.getString("mqversion");
				String candleversion = rs.getString("candleversion");
				String comment = rs.getString("comment");
				String decommissioned = rs.getString("decommissioned");
				String status = rs.getString("status");
				String installed = rs.getString("installed");
				MQMachine mqMachine = new MQMachine(node, dns, ip, description, processors, totalcores,
						pvu, extendedpvu, osversion, mqversion, candleversion, comment, decommissioned,
						status, installed);
				mqMachinesList.add(mqMachine);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return mqMachinesList;
	}*/

	/*public static List<QueueManager> getQueueManagers() {
		List<QueueManager> qmList = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = getConnection().prepareStatement(SELECT_FROM_QUEUEMANAGERS);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				String sla = rs.getString("sla");
				String description = rs.getString("description");
				String machine = rs.getString("machine");
				int port = rs.getInt("port");
				String cluster = rs.getString("cluster");
				String clusterRole = rs.getString("clusterRole");
				String comment = rs.getString("comment");
				String decommissioned = rs.getString("decommissioned");
				String validToYear = rs.getString("validToYear");
				QueueManager qm = new QueueManager(name, sla, description, machine, port, cluster,
						clusterRole, comment, decommissioned, validToYear);
				qmList.add(qm);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return qmList;
	}*/

}
