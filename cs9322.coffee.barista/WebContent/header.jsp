<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="A layout example that shows off a responsive pricing table.">
	
	<title>Barista App - <c:out value="${param.title}" /></title>
	
	<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.4.2/pure.css">
	
	<!--[if lte IE 8]>
		<link rel="stylesheet" href="/cs9322.coffee.barista/css/main-grid-old-ie.css">
	<![endif]-->
	<!--[if gt IE 8]><!-->
		<link rel="stylesheet" href="/cs9322.coffee.barista/css/main-grid.css">
	<!--<![endif]-->
	
	<!--[if lte IE 8]>
	    <link rel="stylesheet" href="/cs9322.coffee.barista/css/layouts/pricing-old-ie.css">
	<![endif]-->
	<!--[if gt IE 8]><!-->
		<link rel="stylesheet" href="/cs9322.coffee.barista/css/layouts/pricing.css">
	<!--<![endif]-->
	
</head>
<body>
	<div class="pure-menu pure-menu-open pure-menu-horizontal">
		<a href="#" class="pure-menu-heading">Barista App</a>
		<ul>
			<li><a href="/cs9322.coffee.barista/barista">Orders</a></li>
		</ul>
	</div>
	<div class="banner">
	    <h1 class="banner-head">
	        Coffee Services.<br>
	    </h1>
	</div>