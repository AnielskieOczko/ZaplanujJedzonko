package pl.coderslab.web;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;


public class EncodingFilter implements Filter {
    public static final Logger log = LogManager.getLogger(EncodingFilter.class);
    private String encoding = "UTF-8";
    private boolean forceEncoding = false;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        if(forceEncoding){ //If force encoding is set then it means that set response stream encoding as well ...
            response.setCharacterEncoding(encoding);
        }
        log.info("Filter activated");
        filterChain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        String encodingParam = filterConfig.getInitParameter("encoding");
        String forceEncoding = filterConfig.getInitParameter("forceEncoding");
        if (encodingParam != null) {
            encoding = encodingParam;
        }
        if (forceEncoding != null) {
            this.forceEncoding = Boolean.parseBoolean(forceEncoding);
        }
    }

    @Override
    public void destroy() {
    }
}
