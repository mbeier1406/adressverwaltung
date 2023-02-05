package com.github.mbeier1406.adressverwaltung.jee;

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

import com.github.mbeier1406.adressverwaltung.jee.model.Person;

/**
 * Servlet implementation class PersonServlet: CRUD-Implementierung für {@linkplain Person} über http.
 */
@WebServlet("/PersonServlet")
public class PersonServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/** Der Datenbank-Service CRUD */
	@EJB
	private DBService<Person> personService;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public PersonServlet() {
        super();
    }

    public void init() throws ServletException {
        if ( personService == null ) throw new UnavailableException("personService ist null.");
        personService.setType(Person.class);
        System.out.println("Initialized!");
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("get: " + request + " / " + response);
		final var prop = request.getParameter("prop");
		final var id = request.getParameter("id");
		final var val = request.getParameter("val");
		System.out.println("id: " + id + "; prop: " + prop + "; val:" + val);
		Person p;
		if ( id != null )
			p = personService.findById(Long.parseLong(id));
		else
			p = personService.findByProperty(prop, val);
		System.out.println("p=" + p);
		response.setContentType("text/html");
		try ( PrintWriter pw = response.getWriter(); ) {
			pw.println(p.toString());
		}
	}

	@Override
	  protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			System.out.println("put: " + request + " / " + response);
			final var p = new Person("Michelle", "Wichtig", new Date(), Person.Geschlecht.WEIBLICH, null);
			System.out.println("p=" + p);
			personService.persist(p);
			response.getWriter().append("Served at: ").append(request.getContextPath()).append(p.toString());
			System.out.println("p=" + p);
//	      int userId = retrieveUserid(req);
//	      String body = inputStreamToString(req.getInputStream());
//	      System.out.println("body: " + body);
//	      UserDataService.Instance.saveUserById(userId, body);
	}

}
