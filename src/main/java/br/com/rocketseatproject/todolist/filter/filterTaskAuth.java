package br.com.rocketseatproject.todolist.filter;
import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.rocketseatproject.todolist.user.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Base64;

@Component
public class filterTaskAuth extends OncePerRequestFilter {
    @Autowired
    IUserRepository userRepository;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {


        var servletPath = request.getServletPath();
        System.out.println("Acessar ServLet-> "+servletPath);

        if(servletPath.startsWith("/task/createtask")){

            System.out.println("Passou no servelethContains...");
            // 🔹 Pega o header Authorization
            var authorization = request.getHeader("Authorization");

            // 🔹 Verifica se está presente e começa com "Basic "
            if (authorization == null || !authorization.startsWith("Basic ") ) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Missing or invalid Authorization header");
                return; // para aqui, não segue a requisição
            }

            // 🔹 Remove o prefixo "Basic " e decodifica
            var authEncoded = authorization.substring("Basic ".length()).trim();
            byte[] authDecode = Base64.getDecoder().decode(authEncoded);
            String authDecodedString = new String(authDecode);

            // 🔹 Exemplo de saída no console
            System.out.println("Authorization decoded: " + authDecodedString);
            String[] account = authDecodedString.split(":");
            String userName = account[0];
            String password = account[1];
            System.out.println("User: "+ userName+"     password: "+password);

            var user = this.userRepository.findByname(userName);
            if(user == null){
                response.sendError(401, "Usuario sem autorização...");
                System.out.println("Entrou no erro 401");
                return;
            } else {
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if(passwordVerify.verified){
                    System.out.println("Entrou no passwordVerify");
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                    return;
                } else {
                    System.out.println("Entrou no erro 432");
                    response.sendError(432, "Combinação invalida... rs");
                    return;
                }
            }
        } else {
            System.out.println("andou reto ");
            filterChain.doFilter(request, response);
        }

    }
}

