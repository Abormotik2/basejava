<%@ page import="com.urise.webapp.model.*" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page import="com.urise.webapp.web.ResumeServlet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><label>
                <input type="text" class="form-control" name="fullName" size=50 value="${resume.fullName}">
            </label></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><label>
                    <input type="text" class="form-control" name="${type.name()}" size=30 value="${resume.getContact(type)}">
                </label></dd>
            </dl>
        </c:forEach>
        <hr>
        <hr>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.Section"/>
            <h2><a>${type.title}</a></h2>
            <c:choose>
                <c:when test="${type=='OBJECTIVE'}">
                    <label>
                        <input type='text' name='${type.name()}' size=90 value='<%=((ContentSection) section).getContent()%>'>
                    </label>
                </c:when>
                <c:when test="${type=='PERSONAL'}">
                    <label>
                        <textarea class="form-control" name='${type.name()}' cols=60 rows=7><%=((ContentSection) section).getContent()%></textarea>
                    </label>
                </c:when>
                <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATION'}">
                    <label>
                        <textarea class="form-control" name='${type.name()}' cols=60
                                  rows=5><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
                    </label>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganizations()%>" varStatus="count">
                        <dl>
                            <dt>Название организации:</dt>
                            <dd><label>
                                <input type="text" name="${type.name()}-${count.index}-name" size=80 value="${org.homePage.name}">
                            </label></dd>
                        </dl>
                        <dl>
                            <dt>Ссылка на организацию:</dt>
                            <dd><label>
                                <input type="text" name="${type.name()}-${count.index}-url" size=80 value="${org.homePage.url}">
                            </label></dd>
                        </dl>
                        <div class = "container">
                            <c:forEach var="stages" items="${org.stages}">
                                <jsp:useBean id="stages" type="com.urise.webapp.model.Organization.Stages"/>
                                <dl>
                                    <dt>Дата начала:</dt>
                                    <dd><label>
                                        <input type="text" name="${type}-${count.index}-stages" size=7 value="<%=DateUtil.jspDataFormatter(stages.getStartDate())%>" placeholder="MM-yyyy">
                                    </label></dd>
                                </dl>
                                <dl>
                                    <dt>Дата окончания:</dt>
                                    <dd><label>
                                        <input type="text" name="${type}-${count.index}-stages" size=7 value="<%=DateUtil.jspDataFormatter(stages.getEndDate())%>" placeholder="MM-yyyy">
                                    </label></dd>
                                </dl>
                                <dl>
                                    <dt>Должность:</dt>
                                    <dd><label>
                                        <input type="text" name="${type}-${count.index}-stages" size=50 value="${stages.title}">
                                    </label></dd>
                                </dl>
                                <dl>
                                    <dt>Обязанности:</dt>
                                    <dd><label>
                                        <textarea class="form-control" name="${type}-${count.index}-stages" rows=5 cols=75>${stages.responsibility}</textarea>
                                    </label></dd>
                                </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>