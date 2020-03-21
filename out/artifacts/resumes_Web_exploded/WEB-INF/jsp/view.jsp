<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.urise.webapp.model.ContentSection" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h1>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h1>
    <p>
    <hr>
    <c:forEach var="contactEntry" items="${resume.contacts}">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
        <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
    </c:forEach>
    <p>
    <hr>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.Section>"/>
        <c:set var="type" value="${sectionEntry.key}"/>
        <c:set var="section" value="${sectionEntry.value}"/>
        <jsp:useBean id="section" type="com.urise.webapp.model.Section"/>
        <tr>
            <h2><a name="type.name">${type.title}</a></h2>
        </tr>
        <c:choose>

            <c:when test="${type=='OBJECTIVE'}">
                <tr>
                    <h3><%=((ContentSection) section).getContent()%>
                    </h3>
                </tr>

            </c:when>
            <c:when test="${type=='PERSONAL'}">
                <tr>
                    <%=((ContentSection) section).getContent()%>
                </tr>
            </c:when>

            <c:when test="${type=='QUALIFICATION' || type=='ACHIEVEMENT'}">
                <tr>
                    <ul>
                        <c:forEach var="item" items="<%=((ListSection) section).getItems()%>">
                            <li>${item}</li>
                        </c:forEach>
                    </ul>
                </tr>
            </c:when>

            <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganizations()%>">
                    <tr>
                        <h3><a href="${org.homePage.url}">${org.homePage.name}</a></h3>
                    </tr>
                    <c:forEach var="stages" items="${org.stages}">
                        <jsp:useBean id="stages" type="com.urise.webapp.model.Organization.Stages"/>
                        <tr>
                            <td>${stages.startDate}${"/"}${stages.endDate}</td>
                            <td><b><br>${stages.title}</b><br>${stages.responsibility}</td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </c:when>
        </c:choose>
    </c:forEach>
    <br/>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>