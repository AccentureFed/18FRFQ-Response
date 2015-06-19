<!DOCTYPE html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Jigsaw"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<!--
		<link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
		-->
		
  		<asset:stylesheet src="application.css"/>
  		<asset:stylesheet src="bootstrap.min.css"/>
		<g:javascript library="jquery" plugin="jquery"/>
		<asset:javascript src="bootstrap.min.js"/>
		<asset:javascript src="app.js"/>
		<asset:link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/>
		<g:layoutHead/>
	</head>
	<body ng-app="jigsaw">
	
		<div class="container" ng-view=""></div>
		
        <!-- build:js scripts/vendor.js -->
        <!-- bower:js -->
        <script src="bower_components/modernizr/modernizr.js"></script>
        <script src="bower_components/jquery/dist/jquery.js"></script>
        <script src="bower_components/bootstrap/dist/js/bootstrap.js"></script>
        <script src="bower_components/Bootflat/bootflat/js/icheck.min.js"></script>
        <script src="bower_components/Bootflat/bootflat/js/jquery.fs.selecter.min.js"></script>
        <script src="bower_components/Bootflat/bootflat/js/jquery.fs.stepper.min.js"></script>
        <script src="bower_components/json3/lib/json3.js"></script>
        <script src="bower_components/angular/angular.js"></script>
        <script src="bower_components/angular-ui-router/release/angular-ui-router.js"></script>
        <script src="bower_components/angular-resource/angular-resource.js"></script>
        <script src="bower_components/angular-cookies/angular-cookies.js"></script>
        <script src="bower_components/angular-sanitize/angular-sanitize.js"></script>
        <script src="bower_components/angular-dynamic-locale/src/tmhDynamicLocale.js"></script>
        <script src="bower_components/angular-local-storage/dist/angular-local-storage.js"></script>
        <script src="bower_components/angular-cache-buster/angular-cache-buster.js"></script>
        <script src="bower_components/ngInfiniteScroll/build/ng-infinite-scroll.js"></script>
        <script src="bower_components/spin.js/spin.js"></script>
        <script src="bower_components/ladda/dist/ladda.min.js"></script>
        <script src="bower_components/angular-ladda/dist/angular-ladda.min.js"></script>
        <!-- endbower -->
        <!-- endbuild -->
        
        <!-- build:js({.tmp,src/main/webapp}) scripts/app.js -->
        <script src="scripts/app/app.js"></script>
        <script src="scripts/app/app.constants.js"></script>
        <script src="scripts/components/auth/auth.service.js"></script>
        <script src="scripts/components/auth/principal.service.js"></script>
        <script src="scripts/components/auth/services/account.service.js"></script>
        <script src="scripts/components/auth/services/activate.service.js"></script>
        <script src="scripts/components/auth/services/password.service.js"></script>
        <script src="scripts/components/auth/services/register.service.js"></script>
        <script src="scripts/components/form/form.directive.js"></script>
        <script src="scripts/components/admin/audits.service.js"></script>
        <script src="scripts/components/admin/logs.service.js"></script>
        <script src="scripts/components/admin/configuration.service.js"></script>
        <script src="scripts/components/admin/monitoring.service.js"></script>
        <script src="scripts/components/navbar/navbar.directive.js"></script>
        <script src="scripts/components/navbar/navbar.controller.js"></script>
        <script src="scripts/components/user/user.service.js"></script>
        <script src="scripts/components/util/truncate.filter.js"></script>
        <script src="scripts/components/util/base64.service.js"></script>
        <script src="scripts/components/util/parse-links.service.js"></script>
        <script src="scripts/app/account/account.js"></script>
        <script src="scripts/app/account/activate/activate.js"></script>
        <script src="scripts/app/account/activate/activate.controller.js"></script>
        <script src="scripts/app/account/logout/logout.js"></script>
        <script src="scripts/app/account/logout/logout.controller.js"></script>
        <script src="scripts/app/account/password/password.js"></script>
        <script src="scripts/app/account/password/password.controller.js"></script>
        <script src="scripts/app/account/password/password.directive.js"></script>
        <script src="scripts/app/account/register/register.js"></script>
        <script src="scripts/app/account/register/register.controller.js"></script>
        <script src="scripts/app/account/settings/settings.js"></script>
        <script src="scripts/app/account/settings/settings.controller.js"></script>
        <script src="scripts/app/admin/admin.js"></script>
        <script src="scripts/app/admin/audits/audits.js"></script>
        <script src="scripts/app/admin/audits/audits.controller.js"></script>
        <script src="scripts/app/admin/configuration/configuration.js"></script>
        <script src="scripts/app/admin/configuration/configuration.controller.js"></script>
        <script src="scripts/app/admin/docs/docs.js"></script>
        <script src="scripts/app/admin/health/health.js"></script>
        <script src="scripts/app/admin/health/health.controller.js"></script>
        <script src="scripts/app/admin/logs/logs.js"></script>
        <script src="scripts/app/admin/logs/logs.controller.js"></script>
        <script src="scripts/app/admin/metrics/metrics.js"></script>
        <script src="scripts/app/admin/metrics/metrics.controller.js"></script>
        <script src="scripts/app/entities/entity.js"></script>
        <script src="scripts/app/error/error.js"></script>
        <script src="scripts/app/main/main.js"></script>
        <script src="scripts/app/main/main.controller.js"></script>
        <script src="scripts/components/auth/services/sessions.service.js"></script>
        <script src="scripts/components/auth/provider/auth.session.service.js"></script>
        <script src="scripts/app/account/sessions/sessions.js"></script>
        <script src="scripts/app/account/sessions/sessions.controller.js"></script>
        <script src="scripts/app/admin/users/user.js"></script>
        <script src="scripts/app/admin/users/user.controller.js"></script>
        <script src="scripts/app/admin/users/user-detail.controller.js"></script>
        <!-- endbuild -->
	</body>
</html>
