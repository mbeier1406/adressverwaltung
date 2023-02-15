package com.github.mbeier1406.adressverwaltung.jee;

import static org.apache.logging.log4j.CloseableThreadContext.put;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.mbeier1406.adressverwaltung.jee.model.Person;

/**
 * CRUD-Implementierung für {@linkplain Person} über http.
 */
@WebServlet("/PersonServlet")
public class PersonServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final Logger LOGGER = LogManager.getLogger(PersonServlet.class);

	/** Der Datenbank-Service CRUD */
	@EJB
	private DBService<Person> personService;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public PersonServlet() {
        super();
    }

    /** Typ des DB-Services setzen */
    @Override
    public void init() throws ServletException {
        if ( personService == null ) throw new UnavailableException("personService ist null.");
        personService.setType(Person.class);
		LOGGER.info("Server: {}", getServletContext().getServerInfo());
    }

	/**
	 * <p>
	 * <code>wget -q -S -O - "http://localhost:8080/adressverwaltung/PersonServlet?id=2601"</code>
	 * </p>
	 * <p>
	 * <code>wget -q -S -O - "http://localhost:8080/adressverwaltung/PersonServlet?prop=vorname&val=Herbert"</code>
	 * </p>
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try ( CloseableThreadContext.Instance ctx = put("request", request.toString()).put("Info", getServletInfo());
				PrintWriter pw = response.getWriter(); ) {
			final var id = request.getParameter("id");
			final var prop = request.getParameter("prop");
			final var val = request.getParameter("val");
			LOGGER.debug("id: {}" + id + "; prop: " + prop + "; val:" + val);
			Person p;
			if ( id != null )
				p = personService.findById(Long.parseLong(id));
			else
				p = personService.findByProperty(prop, val);
			System.out.println("p=" + p);
			response.setContentType("text/html");
			pw.println(p.toString());
		}
		catch ( Exception e ) {
			LOGGER.error("request={}", request, e);
		}
	}

	/**
	 * $ wget --quiet --server-response --output-document=- --method=PUT --body-data "ccc^Mxxx" http://localhost:8080/adressverwaltung/PersonServlet
	 * 
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try ( CloseableThreadContext.Instance ctx = put("request", request.toString()).put("Info", getServletInfo());
				BufferedReader reader = request.getReader();
				PrintWriter pw = response.getWriter() ) {
			for ( String line=""; line != null; line = reader.readLine() )
				LOGGER.info("line={}", line);
//			final var p = new Person("Michelle", "Wichtig", new Date(), Person.Geschlecht.WEIBLICH, null);
//			System.out.println("p=" + p);
//			personService.persist(p);
//			response.getWriter().append("Served at: ").append(request.getContextPath()).append(p.toString());
//			System.out.println("p=" + p);
	//	      int userId = retrieveUserid(req);
	//	      String body = inputStreamToString(req.getInputStream());
	//	      System.out.println("body: " + body);
	//	      UserDataService.Instance.saveUserById(userId, body);
			response.setContentType("text/html");
			pw.println("Gespeichert.");
		}
		catch ( Exception e ) {
			LOGGER.error("request={}", request, e);
		}
	}

	/**	{@inheritDoc} */
	@Override
	public String getServletInfo() {
		return "Adressverwaltung, 2023, mdd";
	}

}
