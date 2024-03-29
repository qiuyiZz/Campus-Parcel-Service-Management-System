package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;

import database.Login;

/**
 * Servlet implementation class sendC
 */
@WebServlet("/sendC")
public class sendC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sendC() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		Login su = new Login("superuser","1234567");
		su.login();
		//获取登录用户信息
		HttpSession isession = request.getSession();
		String userID=(String) isession.getAttribute("userID");
		//应从前端获取到寄件人id
		ArrayList<ArrayList> l = su.select("post,express","post.expressNumber,expressCompany,senderName" + 
				",senderTel,senderAddress,post.recipientName,post.recipientTel,recipientAddress,placetime,payment","senderTel="+userID+" and post.expressNumber=express.expressNumber"); 
				
				ArrayList<result3>lr=(ArrayList<result3>)new ArrayList();
				for(int i=0;i<l.size();i++) {
					
					lr.add(new result3(l.get(i).get(0).toString(),l.get(i).get(1).toString(),l.get(i).get(2).toString(),
							l.get(i).get(3).toString(),l.get(i).get(4).toString(),l.get(i).get(5).toString(),
							l.get(i).get(6).toString(),l.get(i).get(7).toString(),l.get(i).get(8).toString(),
							l.get(i).get(9).toString()));
				}
				
		
				String jsonStringrr = JSON.toJSONString(lr);
				 int num=lr.size();
		 HttpSession session = request.getSession();
		 session.setAttribute("num_sc",""+num);				
		 session.setAttribute("array_sc",jsonStringrr);
			//System.out.println(num);
			//System.out.println(jsonStringrr);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/send_client.jsp");
	        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
