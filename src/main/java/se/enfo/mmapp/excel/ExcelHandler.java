package se.enfo.mmapp.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import se.enfo.mmapp.excel.model.MQMachine;
import se.enfo.mmapp.excel.model.QueueManager;

public class ExcelHandler {

	private static final Logger LOG = Logger.getLogger(ExcelHandler.class);
	private static final int QUEUEMANAGER_SHEET_NUMBER = 1;
	private static final int MQMACHINE_SHEET_NUMBER = 0;
	private static final String PATH_TO_EXCEL = "VCC_Infrastructure_Overview_-_fake.xlsx";

	public List<MQMachine> getMQMachines() {
		final List<MQMachine> mqMachines = new ArrayList<MQMachine>();
		MQMachine mqMachine = null;
		FileInputStream file = null;
		try {
			file = new FileInputStream(new File(PATH_TO_EXCEL));
			final XSSFWorkbook workbook = new XSSFWorkbook(file);
			final XSSFSheet sheet = workbook.getSheetAt(MQMACHINE_SHEET_NUMBER);
			for (final Row row : sheet) {
				final int rowNum = row.getRowNum();
				if (rowNum != 0 && rowNum != 1) {
					mqMachine = new MQMachine();
					for (final Cell cell : row) {
						final int columnIndex = cell.getColumnIndex();
						setMqMachineFieldValue(mqMachine, cell, columnIndex);
					}
					mqMachines.add(mqMachine);
				}
			}
		} catch (final IOException e) {
			LOG.error("Failed to read from file", e);
		}
		finally {
			try {
				file.close();
			} catch (final IOException e) {
				LOG.error("Failed to close file", e);
			}
		}
		return mqMachines;
	}

	private void setMqMachineFieldValue(final MQMachine mqMachine, final Cell cell,
			final int columnIndex) {
		switch (columnIndex) {
		case 0:
			final String node = cell.getStringCellValue();
			mqMachine.setNode(node);
			break;
		case 1:
			final String dns = cell.getStringCellValue();
			mqMachine.setDns(dns);
			break;
		case 2:
			final String ip = cell.getStringCellValue();
			mqMachine.setIp(ip);
			break;
		case 3:
			final String description = cell.getStringCellValue();
			mqMachine.setDescription(description);
			break;
		case 4:
			final int processors = (int) cell.getNumericCellValue();
			mqMachine.setProcessors(processors);
			break;
		case 5:
			final int totalCores = (int) cell.getNumericCellValue();
			mqMachine.setTotalCores(totalCores);
			break;
		case 6:
			final int pvu = (int) cell.getNumericCellValue();
			mqMachine.setPvu(pvu);
			break;
		case 7:
			final int extendedPvu = (int) cell.getNumericCellValue();
			mqMachine.setExtendedPvu(extendedPvu);
			break;
		case 8:
			final String osVersion = cell.getStringCellValue();
			mqMachine.setOsVersion(osVersion);
			break;
		case 9:
			final String mqVersion = cell.getStringCellValue();
			mqMachine.setMqVersion(mqVersion);
			break;
		case 10:
			final String candleVersion = cell.getStringCellValue();
			mqMachine.setCandleVersion(candleVersion);
			break;
		case 11:
			final String comment = cell.getStringCellValue();
			mqMachine.setComment(comment);
			break;
		case 12:
			final String decomissioned = cell.getStringCellValue();
			mqMachine.setDecommissioned(decomissioned);
			break;
		case 13:
			final String status = cell.getStringCellValue();
			mqMachine.setStatus(status);
			break;
		case 14:
			final String installed = cell.getStringCellValue();
			mqMachine.setInstalled(installed);
			break;
		}
	}

	public List<QueueManager> getQueueManagers() {
		final List<QueueManager> queueManagers = new ArrayList<QueueManager>();
		QueueManager queueManager = null;
		FileInputStream file = null;
		try {
			file = new FileInputStream(new File(PATH_TO_EXCEL));
			final XSSFWorkbook workbook = new XSSFWorkbook(file);
			final XSSFSheet sheet = workbook.getSheetAt(QUEUEMANAGER_SHEET_NUMBER);
			for (final Row row : sheet) {
				final int rowNum = row.getRowNum();
				if (rowNum != 0 && rowNum != 1) {
					queueManager = new QueueManager();
					for (final Cell cell : row) {
						final int columnIndex = cell.getColumnIndex();
						setQueueManagerFieldValue(queueManager, cell, columnIndex);
					}
					queueManagers.add(queueManager);
				}
			}
		} catch (final IOException e) {
			LOG.error("Failed to read from file", e);
		} finally {
			try {
				file.close();
			} catch (final IOException e) {
				LOG.error("Failed to close file", e);
			}
		}
		return queueManagers;
	}

	private void setQueueManagerFieldValue(final QueueManager queueManager,

			final Cell cell, final int columnIndex) {
		switch (columnIndex) {
		case 0:
			final String name = cell.getStringCellValue();
			queueManager.setName(name);
			break;
		case 1:
			final String sla = cell.getStringCellValue();
			queueManager.setSla(sla);
			break;
		case 2:
			final String description = cell.getStringCellValue();
			queueManager.setDescription(description);
			break;
		case 3:
			final String machine = cell.getStringCellValue();
			queueManager.setMachine(machine);
			break;
		case 4:
			final int port = (int) cell.getNumericCellValue();
			queueManager.setPort(port);
			break;
		case 5:
			final String cluster = cell.getStringCellValue();
			queueManager.setCluster(cluster);
			break;
		case 6:
			final String clusterRole = cell.getStringCellValue();
			queueManager.setClusterRoll(clusterRole);
			break;
		case 7:
			final String comment = cell.getStringCellValue();
			queueManager.setComment(comment);
			break;
		case 8:
			final String decommissioned = cell.getStringCellValue();
			queueManager.setDecommissioned(decommissioned);
			break;
		case 9:
			final String validToYear = cell.getStringCellValue();
			queueManager.setValidToYear(validToYear);
			break;
		}
	}

}
