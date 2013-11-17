package se.enfo.mmapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.enfo.mmapp.dao.Dao;
import se.enfo.mmapp.excel.ExcelHandler;
import se.enfo.mmapp.excel.model.QueueManager;

/**
 * Servlet implementation class QueueManagers
 */
@WebServlet("/QueueManagers")
public class QueueManagers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final ExcelHandler excelHandler = new ExcelHandler();
		final List<QueueManager> qmList = excelHandler.getQueueManagers();
		for (final QueueManager queueManager : qmList) {
			saveQMRecord(queueManager);
		}
		response.setContentType("text/html");
		final PrintWriter writer = response.getWriter();
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
				+ "<li><a href='#'>MQ Machines</a></li>"
				+ "<li class='divider-vertical'></li>"
				+ "<li class='active'><a href='#''>Queue Managers</a></li>"
				+ "</div>"
				+ "</div>"
				+ "<div class='page-header'>"
				+ "<h1 align='center'>QUEUE MANAGERS</h1>"
				+ "</div>");
		writer.println("<table class='table table-bordered table-striped table-hover table-condensed'>"
				+ "<tr>"
				+ "<th>Name</th>"
				+ "<th>SLA</th>"
				+ "<th>Description</th>"
				+ "<th>Machine</th>"
				+ "<th>Port</th>"
				+ "<th>Cluster</th>"
				+ "<th>Cluster role</th>"
				+ "<th>Comment</th>"
				+ "<th>Decommissioned</th>"
				+ "<th>Valid to year</th>"
				+ "</tr>");
		for (final QueueManager qm : qmList) {
			writer.println("<tr>"
					+ "<td>" + qm.getName() + "</td>"
					+ "<td>" + qm.getSla() + "</td>"
					+ "<td>" + qm.getDescription() + "</td>"
					+ "<td>" + qm.getMachine() + "</td>"
					+ "<td>" + qm.getPort() + "</td>"
					+ "<td>" + qm.getCluster() + "</td>"
					+ "<td>" + qm.getClusterRole() + "</td>"
					+ "<td>" + qm.getComment() + "</td>"
					+ "<td>" + qm.getDecommissioned() + "</td>"
					+ "<td>" + qm.getValidToYear() + "</td>"
					+ "</tr>");
		}
		writer.println("</table>"
				+ "<script src='http://code.jquery.com/jquery.js'></script>"
				+ "<script src='/webapp/js/bootstrap.min.js'></script>"
				+ "</body>"
				+ "</html>");
	}

	private void saveQMRecord(final QueueManager queueManager) {
		Dao.insertQueueManager(queueManager);
	}

}
