<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="regularexpressionExpression" scope="session" class="fr.paris.lutece.plugins.regularexpression.web.RegularExpressionJspBean" />

<% 
	regularexpressionExpression.init( request, fr.paris.lutece.plugins.regularexpression.web.RegularExpressionJspBean.RIGHT_REGULAR_EXPRESSION_MANAGEMENT );
    response.sendRedirect(  regularexpressionExpression.getConfirmRemoveRegularExpression(request) );
%>
