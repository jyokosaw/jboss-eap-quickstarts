package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SessionServlet")
public class SessionServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        System.out.println("### Called! --  SessionServlet#doGet()");

        // For test
        /*
        System.out.println("### SessionServlet sleep 300 milliseconds");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            ;
        }
        */

        HttpSession session = req.getSession(true);

        String serverName;
        String failoverMessage = "";
        String str = (String) session.getAttribute("simplesession.servername");
        PrintWriter out = res.getWriter();
        Integer ival;

        res.setContentType("text/html");

        ival = (Integer) session.getAttribute("sessiontest.counter");
        if (ival == null) ival = new Integer(1);
        else ival = new Integer(ival.intValue() + 1);

        session.setAttribute("sessiontest.counter", ival);

        out.println("<HTML><HEAD><TITLE>Session Servlet</TITLE></HEAD><BODY>");

        try {
            out.println("You have hit this page <b>" + ival + "</b> times.<p>");
            out.println("Click <a href=" + res.encodeURL("SessionServlet") +
                    ">here</a>");
            out.println(" to ensure that session tracking is working even " +
                    "if cookies aren't supported.<br>");
            out.println("Note that by default URL rewriting is not enabled " +
                    "because of its expensive overhead");
            out.println("<p>");
            out.println("<h4>Request and Session Data:</h4>");
            out.println("Session ID in Request: " + req.getRequestedSessionId());
            out.println("<br>Session ID in Request from Cookie: " +
                    req.isRequestedSessionIdFromCookie());
            out.println("<br>Session ID in Request from URL: " +
                    req.isRequestedSessionIdFromURL());
            out.println("<br>Valid Session ID: " + req.isRequestedSessionIdValid());
            out.println("<h4>Session Data:</h4>");
            out.println("New Session: " + session.isNew());
            out.println("<br>Session ID: " + session.getId());
            out.println("<br>Creation Time: " + session.getCreationTime());
            out.println("<br>Last Accessed Time: " + session.getLastAccessedTime());
        } catch (Exception ex) {
            out.println("<p><b>!! Example Failed !!<br><br> The following exception occur:</b><br><br>");
            ex.printStackTrace(new PrintWriter(out));
            ex.printStackTrace();
        } finally {
            out.println("</BODY></HTML>");

        }
    }
}
