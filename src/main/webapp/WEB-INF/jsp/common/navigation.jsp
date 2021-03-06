<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <a class="navbar-brand" href="index">  스터디 매니저 </a>
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>

	            <ul class="nav navbar-top-links navbar-right">
	                <!-- /.dropdown -->
                    <c:if test="${alertcount>0}">
		                <li class="dropdown">
		                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" onclick="showAlertList()">
		                        <i class="fa fa-bell fa-fw"></i>  <i class="fa fa-caret-down"></i>
		                        	<div class="msgAlert"><c:out value="${alertcount}"/></div>
		                    </a>
		                    <script>
		                    	function showAlertList(){
		                    		$.ajax({
		                    			url: "alertList4Ajax", 
		                    			dataType: "html",
		                    			type:"post", 
		                    			success: function(result){
		                    				if (result!=="") {
		                    					$("#alertlist").html(result);
		                    				}
		                    			}
		                    		})		                    		
		                    	}
		                    </script>
		                    <ul id="alertlist" class="dropdown-menu dropdown-alerts">
		                    </ul>
		                    <!-- /.dropdown-alerts -->
		                </li>
                    </c:if>
	                <!-- /.dropdown -->
	                <li class="dropdown">
	                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
	                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
	                    </a>
	                    <ul class="dropdown-menu dropdown-user">
	                        <li><a href="userInfo"><i class="fa fa-user fa-fw"></i> 내 정보</a></li>
	                        <li><a href="searchMember"><i class="fa fa-users fa-fw"></i> menu </a></li>
	                        <li class="divider"></li>
	                        <li><a href="userLogout"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
	                        </li>
	                    </ul>
	                    <!-- /.dropdown-user -->
	                </li>
	                <!-- /.dropdown -->
	            </ul>
	            <!-- /.navbar-top-links -->
            </div>
            <!-- /.navbar-header -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li class="sidebar-search">
                           	<form id="searchForm" name="searchForm"  method="post" action="boardList">
                                <input type="hidden" name="searchType" value="brdtitle,brdmemo">
								<div class="input-group custom-search-form">
	                                <input class="form-control" type="text" name="globalKeyword" id="globalKeyword" placeholder="Search...">
	                                <span class="input-group-btn">
	                                    <button class="btn btn-default" type="button" onclick="fn_search()">
	                                        <i class="fa fa-search"></i>
	                                    </button>
	                                </span>
	                            </div>                           	
                            </form>
	                                <script>
	                                	function fn_search(){
	                                		if ($("#globalKeyword").val()!=="") {
		                                		$("#searchForm").submit();
	                                		}
	                                	}
	                                </script>                            <!-- /input-group -->
                        </li>
                        
                        <c:if test='${sessionScope.userrole == "A"}'>
	                        <li>
	                            <a href="adminUserList"><i class="fa fa-files-o fa-fw"></i> 사용자 관리 </a>
	                        </li>
                        </c:if>
                        
                        <li>
                            <a href="studyList"><i class="fa fa-files-o fa-fw"></i> 스터디 리스트 </a>
                        </li>
                        <li>
                            <a href="projectList"><i class="fa fa-tasks fa-fw"></i> 스터디 매니저 </a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-music fa-fw"></i> 샘플<span class="fa arrow"></span></a>
							<ul class="nav nav-second-level">
                                <li>
                                    <a href="sample1">샘플 1: 조직도/사용자</a>
                                </li>
                                <li>
		                            <a href="sample2">샘플 2: 날짜 선택 </a>
                                </li>
                                <li>
		                            <a href="sample3">샘플 3: 챠트</a>
                                </li>
                                <li>
		                            <a href="sample4">샘플 4: List & Excel</a>
                                </li>
                                <li>
		                            <a href="crudList">샘플 5: CRUD</a>
                                </li>
	                        </ul>                            
                        </li>
                        <c:if test='${sessionScope.userrole == "A"}'>
	                        <li>
	                            <a href="#">   code="memu.admin"/></a>
	                        </li>
	                        <li>
	                            <a href="adBoardGroupList"><i class="fa fa-files-o fa-fw"></i>   code="memu.board"/></a>
	                        </li>
	                        <li>
	                            <a href="#"><i class="fa fa-sitemap fa-fw"></i>   code="memu.organ"/><span class="fa arrow"></span></a>
	                            <ul class="nav nav-second-level">
	                                <li>
	                                    <a href="adDepartment">  code="memu.dept"/></a>
	                                </li>
	                                <li>
	                                    <a href="adUser">  code="memu.user"/></a>
	                                </li>
	                            </ul>
	                        </li>
                             <li>
                                 <a href="adCodeList"><i class="fa fa-gear fa-fw"></i>   code="memu.code"/></a>
                             </li>
	                	</c:if>	        
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>

