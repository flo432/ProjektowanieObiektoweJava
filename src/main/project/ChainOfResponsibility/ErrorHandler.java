package ChainOfResponsibility;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class ErrorHandler extends Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "<!DOCTYPE HTML> <html> <head> <title>Error 404</title> <style> /* reset */ html,body,div,span,applet,object,iframe,h1,h2,h3,h4,h5,h6,p,blockquote,pre,a,abbr,acronym,address,big,cite,code,del,dfn,em,img,ins,kbd,q,s,samp,small,strike,strong,sub,sup,tt,var,b,u,i,dl,dt,dd,ol,nav ul,nav li,fieldset,form,label,legend,table,caption,tbody,tfoot,thead,tr,th,td,article,aside,canvas,details,embed,figure,figcaption,footer,header,hgroup,menu,nav,output,ruby,section,summary,time,mark,audio,video{margin:0;padding:0;border:0;font-size:100%;font:inherit;vertical-align:baseline;} article, aside, details, figcaption, figure,footer, header, hgroup, menu, nav, section {display: block;} ol,ul{list-style:none;margin:0px;padding:0px;} blockquote,q{quotes:none;} blockquote:before,blockquote:after,q:before,q:after{content:'';content:none;} table{border-collapse:collapse;border-spacing:0;} /* start editing from here */ a{text-decoration:none;} .txt-rt{text-align:right;}/* text align right */ .txt-lt{text-align:left;}/* text align left */ .txt-center{text-align:center;}/* text align center */ .float-rt{float:right;}/* float right */ .float-lt{float:left;}/* float left */ .clear{clear:both;}/* clear float */ .pos-relative{position:relative;}/* Position Relative */ .pos-absolute{position:absolute;}/* Position Absolute */ .vertical-base{ vertical-align:baseline;}/* vertical align baseline */ .vertical-top{ vertical-align:top;}/* vertical align top */ .underline{ padding-bottom:5px; border-bottom: 1px solid #eee; margin:0 0 20px 0;}/* Add 5px bottom padding and a underline */ nav.vertical ul li{ display:block;}/* vertical menu */ nav.horizontal ul li{ display: inline-block;}/* horizontal menu */ img{max-width:100%;} /*end reset* */ .content p{ margin: 18px 0px 45px 0px; } .content p{ font-size:2em; color:#666; text-align:center; } .content p span,.logo h1 a{ color:#e54040; } .content{ text-align:center; padding:115px 0px 0px 0px; } .content a{ color:#fff; background: #666666; /* Old browsers */ background: -moz-linear-gradient(top, #666666 0%, #666666 100%); /* FF3.6+ */ background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#666666), color-stop(100%,#666666)); /* Chrome,Safari4+ */ background: -webkit-linear-gradient(top, #666666 0%,#666666 100%); /* Chrome10+,Safari5.1+ */ background: -o-linear-gradient(top, #666666 0%,#666666 100%); /* Opera 11.10+ */ background: -ms-linear-gradient(top, #666666 0%,#666666 100%); /* IE10+ */ background: linear-gradient(to bottom, #666666 0%,#666666 100%); /* W3C */ filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#666666', endColorstr='#666666',GradientType=0 ); /* IE6-9 */ padding: 15px 20px; border-radius: 1em; } .content a:hover{ color:#e54040; } /*------responive-design--------*/ @media screen and (max-width: 1366px) { .content { padding: 58px 0px 0px 0px; } } </style> </head> <body> <div class=\"wrap\"> <div class=\"header\"> </div> <div class=\"content\"> <img src=\"http://wstaw.org/m/2018/02/06/proxy.duckduckgo.com.jpg\" title=\"error\" /> <p><span><label>O</label>hh.....</span>You Requested the ghost page.</p> <a href=\"/home\">Back To Home</a> </div> </div> </body> </html>";
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
