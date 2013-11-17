package se.enfo.mmapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import se.enfo.mmapp.dao.Dao;
import se.enfo.mmapp.excel.ExcelHandler;
import se.enfo.mmapp.excel.model.MQMachine;

/**
 * Servlet implementation class MqMachines
 */
@WebServlet("/MqMachines")
public class MqMachines extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(MqMachines.class);

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
		final ExcelHandler excelHandler = new ExcelHandler();
		final List<MQMachine> mqMachines = excelHandler.getMQMachines();
		for (final MQMachine mqMachine : mqMachines) {
			saveMqMachineRecord(mqMachine);
		}
		response.setContentType("text/html");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (final IOException e) {
			LOG.error(e);
		}
		writer.print("<html>"
				+ "<head>"
				+ "<title>title</title>"
				+ "<link rel='stylesheet' href='css/bootstrap.css' type='text/css'/>"
				+ "<link rel='stylesheet' href='css/mystyle.css' type='text/css'/>"
				+ "</head>"
				+ "<body>"
				+ "<div class='navbar'>"
				+ "<div class='navbar-inner'>"
				+ "<a class='brand' href='#'>Manual Monitoring Application</a>"
				+ "<ul class='nav'>"
				+ "<li><a href='#'>Main</a></li>"
				+ "<li class='divider-vertical'></li>"
				+ "<li class='active'><a href='#'>MQ Machines</a></li>"
				+ "<li class='divider-vertical'></li>"
				+ "<li><a href='#''>Queue Managers</a></li>"
				+ "</div>"
				+ "</div>"
				+ "<div class='page-header'>"
				+ "<h1 align='center'>MQ Machines</h1>"
				+ "</div>");
		writer.println("<table class='table table-bordered table-striped table-hover table-condensed'>"
				+ "<tr>"
				+ "<th>Node</th>"
				+ "<th>DNS</th>"
				+ "<th>IP</th>"
				+ "<th>Description</th>"
				+ "<th>Processors</th>"
				+ "<th>Total Cores per Partition or VM</th>"
				+ "<th>PVUs per core</th>"
				+ "<th>Extended PVU quantity</th>"
				+ "<th>OS Version</th>"
				+ "<th>MQ Version</th>"
				+ "<th>Candle Version</th>"
				+ "<th>Comment</th>"
				+ "<th>Decommissioned</th>"
				+ "<th>Status</th>"
				+ "<th>Where expect is installed (change the same in the script)</th>"
				+ "</tr>");
		for (final MQMachine mqMachine : mqMachines) {
			writer.println("<tr>"
					+ "<td>" + mqMachine.getNode() + "</td>"
					+ "<td>" + mqMachine.getDns() + "</td>"
					+ "<td>" + mqMachine.getIp() + "</td>"
					+ "<td>" + mqMachine.getDescription() + "</td>"
					+ "<td>" + mqMachine.getProcessors() + "</td>"
					+ "<td>" + mqMachine.getTotalCores() + "</td>"
					+ "<td>" + mqMachine.getPvu() + "</td>"
					+ "<td>" + mqMachine.getExtendedPvu() + "</td>"
					+ "<td>" + mqMachine.getOsVersion() + "</td>"
					+ "<td>" + mqMachine.getMqVersion() + "</td>"
					+ "<td>" + mqMachine.getCandleVersion() + "</td>"
					+ "<td>" + mqMachine.getComment() + "</td>"
					+ "<td>" + mqMachine.getDecommisioned() + "</td>"
					+ "<td>" + mqMachine.getStatus() + "</td>"
					+ "<td>" + mqMachine.getInstalled() + "</td>"
					+ "</tr>");
		}
		writer.println("</table>"
				+ "<script src='http://code.jquery.com/jquery.js'></script>"
				+ "<script src='/webapp/js/bootstrap.min.js'></script>"
				+ "</body>"
				+ "</html>");
	}

	private void saveMqMachineRecord(final MQMachine mqMachine) {
		Dao.insertMQMachine(mqMachine);
	}

}













