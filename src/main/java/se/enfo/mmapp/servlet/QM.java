package se.enfo.mmapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import se.enfo.mmapp.MMApp;

/**
 * Servlet implementation class QM
 */
@WebServlet("/QM")
public class QM extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(QM.class);

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final MMApp app = new MMApp();
		LOG.info("QM application start");
		app.processQM();
		LOG.info("QM application finish");
	}

}
