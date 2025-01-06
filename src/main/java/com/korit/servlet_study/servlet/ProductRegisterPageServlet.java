package com.korit.servlet_study.servlet;

import com.korit.servlet_study.datas.DataList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// http://localhost:8080/servlet_study/product/register (GET)
@WebServlet("/product/register")
public class ProductRegisterPageServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        servletContext.setAttribute("serverName", "서블릿 학습");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("상품등록 페이지 doGet 호출");

        HttpSession session = request.getSession();
        session.setAttribute("username", "junil");

        request.setAttribute("categories", DataList.getCategoryList());
        request.getRequestDispatcher("/WEB-INF/product_register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("저장");

        System.out.println(request.getParameter("category"));
        System.out.println(request.getParameter("productName"));
        System.out.println(request.getParameter("price"));
        System.out.println(request.getParameter("registerDate"));

//        response.setContentType("text/html;charset=utf-8"); // filter 가 있어도, setContentType 은 해줘야함! json으로, html로 각각 알맞게 호출해줘야하기 때문!
        response.setContentType("application/json");
        response.setStatus(200); // http 상태 코드
        response.getWriter().println("{\"name\":\"김준일\"}");
//        response.getWriter().println(
//                "<script>"
//                        + "alert(\"등록이 완료되었습니다.\");"
//                        + "location.href='http://localhost:8080/servlet_study_war/product/register'"
//                        + "</script>"
//        );
    }
}
