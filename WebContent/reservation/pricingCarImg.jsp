<%@page import="com.itwillbs.chaca.db.CarDTO"%>
<%
	String selectedCarVal = request.getParameter("selectedCarVal");
	System.out.println(selectedCarVal);
	
// 	CarDTO dto = new CarDTO();
	
// 	dto.setCar_name(selectedCarVal);


	if(selectedCarVal.equals("2 series")){
		%> <img src="../car_images/BMW_2series.jpg"> <%
	}
	

	
%>