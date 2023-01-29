package com.github.mbeier1406.adressverwaltung.jee;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.mbeier1406.adressverwaltung.jee.model.Person;
import com.github.mbeier1406.adressverwaltung.jee.model.PersonImpl;

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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final var p = new PersonImpl("Michelle", "Wichtig", new Date(), Person.Geschlecht.WEIBLICH, null);
		System.out.println("p="+p);
		personService.persist(p);
		response.getWriter().append("Served at: ").append(request.getContextPath()).append(p.toString());
		System.out.println("p="+p);
	}

}
