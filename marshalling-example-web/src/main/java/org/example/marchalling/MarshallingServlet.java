package org.example.marchalling;

import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;

@WebServlet("/marshalling")
public class MarshallingServlet extends HttpServlet {

    @Inject
    EchoBean echoBean;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException {

        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("river");
        MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(3);

        Marshaller marshaller = marshallerFactory.createMarshaller(configuration);
        try (FileOutputStream os = new FileOutputStream("/Users/jyokosaw/tmp/eap-marshalling-test-obj.dat")) {
            marshaller.start(Marshalling.createByteOutput(os));
            System.out.println("-----");
            marshaller.writeObject(echoBean);  // serialize ejb client proxy object for Singleton Session Bean which contains a reference to org.
            System.out.println("-----");
            marshaller.finish();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        resp.setContentType("text/plain; charset=utf-8");
        resp.getWriter().println("object marshalling done");
    }
}
