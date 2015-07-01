<!DOCTYPE html>
<html>
<head>
<title>Jigsaw Login</title>
<link type="text/css" rel="stylesheet"
	href="${resource(dir: 'css', file: 'login.css')}" />
<asset:stylesheet src="bootstrap/dist/css/bootstrap.min.css"/>
<asset:javascript src="jquery/dist/jquery.min.js"/>
</head>
<body>
	<nav class="navbar navbar-inverse" role="navigation">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<a class="navbar-brand"
				href="${createLink(controller: 'login', action: 'index')}">Jigsaw</a>
		</div>
	</nav>

	<div class="container">

		<form action="${postUrl}" method="POST" class="form-signin" id="loginForm" autocomplete="off">
			<h2 class="form-signin-heading">Please sign in</h2>

			<g:if test="${flash.message}">
				<div class="alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">&times;</button>
					${flash.message}
				</div>
			</g:if>

			<input type="text" class="form-control" placeholder="username"
				id="username" name="j_username" />
			<input type="password"
				class="form-control" placeholder="password" id="password"
				name="j_password" />
			<input type="text" class="form-control" style="display: none;" 
			    id="forwardURI" name="forwardURI" value="${forwardURI}" />
			<button class="btn btn-lg btn-primary btn-block btn-tint" type="submit">Sign
				in</button>
		</form>

	</div>
</body>
</html>