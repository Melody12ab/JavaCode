<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!synchronized void count() {
		ServletContext application = getServletContext();
		Integer num = (Integer) application.getAttribute("num");
		if (num == null) {
			num = new Integer(1);
			application.setAttribute("num", num);
		} else {
			num = new Integer(1 + num);
			application.setAttribute("num", num);
		}
	}%>
<%
	if (session.isNew()) { //为了避免用户的刷新的问题
		count();
	}
	Integer tNum = (Integer) application.getAttribute("num");
%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<!-- BASICS -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Web开发兴趣组</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="css/isotope.css"
	media="screen" />
<link rel="stylesheet" href="js/fancybox/jquery.fancybox.css"
	type="text/css" media="screen" />
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/bootstrap-theme.css">
<link rel="stylesheet" href="skin/default.css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
	<section id="header" class="appear"></section>
	<div class="navbar navbar-fixed-top" role="navigation"
		data-0="line-height:100px; height:100px; background-color:rgba(0,0,0,0.3);"
		data-300="line-height:60px; height:60px; background-color:rgba(0,0,0,1);">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="fa fa-bars color-white"></span>
				</button>
				<h1>
					<a class="navbar-brand" href="index.html"
						data-0="line-height:90px;" data-300="line-height:50px;">
						MathWebTeam </a>
				</h1>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav" data-0="margin-top:20px;"
					data-300="margin-top:5px;">
					<!--<li class="active"><a href="index.html">主页</a></li>
						<li><a href="#section-about">相关</a></li>-->
					<li><a href="#section-contact">联系我们</a></li>
				</ul>
			</div>
			<!--/.navbar-collapse -->
		</div>
	</div>

	<section class="featured">
		<div class="container">
			<div class="row mar-bot40">
				<div class="col-md-6 col-md-offset-3">

					<div class="align-center">
						<i class="fa fa-code fa-5x mar-bot20"></i>
						<h2 class="slogan">Welcome to MathWebTeam</h2>
						<p>MathWebTeam成立于2003年，是一个以web开发为主的兴趣组，一直以来都为数学院的同学们提供着
							一个平台和环境去学习自己喜爱好的东西，在这里，只要你想，你就能有收获。不管想做学霸，还是做技术达人，你都能找到合适的方式去实现你的爱好。
						</p>
						<p>WEB开发兴趣组的成员梯队以高年级(大四、大三)项目成员为主、低年级(大二、大一)学生成员为辅。指导老师进行全程培养和研学引导，团队成员共同合作应用开发，分组组长协助管理。组员围绕创新实验项目开发任务，完成实践项目作品。指导教师对兴趣组成员的组织和培养，是基于学生的开发研学兴趣和择业考研方向为大前提，每学年组织开展面向全院在读本科生的招新，学生自主报名参加。每期新报名的学生以大一、大二的学生为主(15-20名左右)，并在大三、大四的学生中进行增补报名（6-8名左右），在两个月的强化培训后通过考核选拔的同学将成为WEB开发兴趣组的正式成员。未在统一招新期间报名的同学，可以在学习期间与指导教师联系，并单独提交参加申请。报名同学在指导教师和正式组员的带动下，了解学习梯队式互助学习与团队协作开发训练的方式，进行WEB开发技术的学习和计算机应用能力锻炼等。
						</p>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- services -->
	<section id="section-services" class="section pad-bot30 bg-white">
		<div class="container">

			<div class="row mar-bot40">
				<div class="col-lg-4">
					<div class="align-center">
						<img src="img/1.png" />
						<h4 class="text-bold">Video</h4>
						<p>来这里你将拥有丰富的视频学习资料，从Linux到Windows，从PHP到Jsp，从Java到三大框架
							等等。来吧，在这里有很多名师面对面教学。</p>
					</div>
				</div>

				<div class="col-lg-4">
					<div class="align-center">
						<img src="img/2.png" />
						<h4 class="text-bold">Book</h4>
						<p>小组拥有大量的图书资料，是我们自己的图书馆。从最基础的操作系统、计算机组成原理、数据结构
							到Java家族、PHP、Spring等。在这里你还会接触了解到什么是大数据，什么叫数据挖掘。来吧，在这里海洋也很大。</p>
					</div>
				</div>

				<div class="col-lg-4">
					<div class="align-center">
						<img src="img/3.png" />
						<h4 class="text-bold">Connection</h4>
						<p>正如开篇所提到的，小组成立于2000年，在你的前面有好多师兄在多年前就在社会体验生活，
							在你的前面有许多学长在各地高校攻读研究生， 在你的前面有好多技术人才正在创作。 来吧，在这里方向的前面总有座灯塔。</p>
					</div>
				</div>

			</div>

		</div>
	</section>

	<section id="testimonials" class="section"
		data-stellar-background-ratio="0.5">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="align-center">
						<div class="testimonial pad-top40 pad-bot40 clearfix">
							<h3>
								<font color="white"> Don't aim for success if you want
									it; just do what you love and believe in, and it will come
									naturally. 
							</h3>
							<h3>
								<font color="white">
									如果你想要成功，不要去追求成功；尽管做你自己热爱的事情并且相信它，成功自然到来 
							</h3>
						</div>

					</div>
				</div>
			</div>

		</div>
		</div>
	</section>

	<!-- about -->
	<section id="section-about" class="section appear clearfix">
		<div class="container">

			<div class="row mar-bot40">
				<div class="col-md-offset-3 col-md-6">
					<div class="section-header">
						<h2 class="section-heading animated" data-animation="bounceInUp">我们的团队</h2>
						<p style="color: black">我们在乎组员个人的成长，但是一个人的力量是有限的，而在一个优秀的团队能让我们学到更多，做到更多，这里不仅有厉害的程序猿们，还有各种厉害的学霸和技术达人</p>
						<a class="btn btn-primary" href="details.jsp" role="button">想要详细了解我们
							»</a>
					</div>
				</div>
			</div>

		</div>
		</div>
	</section>
	<!-- /about -->

	<!-- spacer section:stats -->
	<section id="parallax1" class="section pad-top40 pad-bot40"
		data-stellar-background-ratio="0.5">
		<div class="container">
			<div class="align-center pad-top40 pad-bot40">
				<blockquote class="bigquote color-white">欢迎加入我们！一齐协手前进！</blockquote>
			</div>
		</div>
	</section>

	<!-- section works -->
	<section id="section-works" class="section appear clearfix">
		<div class="container">

			<div class="row mar-bot40">
				<div class="col-md-offset-3 col-md-6">
					<div class="section-header">
						<h2 class="section-heading animated" data-animation="bounceInUp">相册</h2>
						<p style="color: black">记录了我们小组生活的点点滴滴</p>
					</div>
				</div>
			</div>

			<div class="row">
				<nav id="filter" class="col-md-12 text-center">
					<ul>
						<li><a href="#" class="current btn-theme btn-small"
							data-filter="*">ALL</a></li>
						<li><a href="#" class="btn-theme btn-small"
							data-filter=".webdesign">部分全家福</a></li>
						<li><a href="#" class="btn-theme btn-small"
							data-filter=".photography">比赛海报</a></li>
					</ul>
				</nav>
				<div class="col-md-12">
					<div class="row">
						<div class="portfolio-items isotopeWrapper clearfix" id="3">

							<article class="col-md-4 isotopeItem webdesign">
								<div class="portfolio-item">
									<img src="img/portfolio/02.jpg" alt="" />
									<div class="portfolio-desc align-center">
										<div class="folio-info">
											<a href="img/portfolio/02.jpg" class="fancybox"><i
												class="fa fa-plus fa-2x"></i></a>
										</div>
									</div>
								</div>
							</article>

							<article class="col-md-4 isotopeItem webdesign">
								<div class="portfolio-item">
									<img src="img/portfolio/03.jpg" alt="" />
									<div class="portfolio-desc align-center">
										<div class="folio-info">
											<a href="img/portfolio/03.jpg" class="fancybox"><i
												class="fa fa-plus fa-2x"></i></a>
										</div>
									</div>
								</div>
							</article>


							<article class="col-md-4 isotopeItem webdesign">
								<div class="portfolio-item">
									<img src="img/portfolio/04.jpg" alt="" />
									<div class="portfolio-desc align-center">
										<div class="folio-info">
											<a href="img/portfolio/04.jpg" class="fancybox"><i
												class="fa fa-plus fa-2x"></i></a>
										</div>
									</div>
								</div>
							</article>

							<article class="col-md-4 isotopeItem webdesign">
								<div class="portfolio-item">
									<img src="img/portfolio/05.jpg" alt="" />
									<div class="portfolio-desc align-center">
										<div class="folio-info">
											<a href="img/portfolio/05.jpg" class="fancybox"><i
												class="fa fa-plus fa-2x"></i></a>
										</div>
									</div>
								</div>
							</article>

							<article class="col-md-4 isotopeItem webdesign">
								<div class="portfolio-item">
									<img src="img/portfolio/06.jpg" alt="" />
									<div class="portfolio-desc align-center">
										<div class="folio-info">
											<a href="img/portfolio/06.jpg" class="fancybox"><i
												class="fa fa-plus fa-2x"></i></a>
										</div>
									</div>
								</div>
							</article>

							<article class="col-md-4 isotopeItem webdesign">
								<div class="portfolio-item">
									<img src="img/portfolio/04.jpg" alt="" />
									<div class="portfolio-desc align-center">
										<div class="folio-info">
											<a href="img/portfolio/04.jpg" class="fancybox"><i
												class="fa fa-plus fa-2x"></i></a>
										</div>
									</div>
								</div>
							</article>

							<article class="col-md-4 isotopeItem photography">
								<div class="portfolio-item">
									<img src="img/portfolio/07.jpg" alt="" />
									<div class="portfolio-desc align-center">
										<div class="folio-info">
											<a href="img/portfolio/07.jpg" class="fancybox"><i
												class="fa fa-plus fa-2x"></i></a>
										</div>
									</div>
								</div>
							</article>

							<article class="col-md-4 isotopeItem photography">
								<div class="portfolio-item">
									<img src="img/portfolio/08.jpg" alt="" />
									<div class="portfolio-desc align-center">
										<div class="folio-info">
											<a href="img/portfolio/08.jpg" class="fancybox"><i
												class="fa fa-plus fa-2x"></i></a>
										</div>
									</div>
								</div>
							</article>

							<article class="col-md-4 isotopeItem photography">
								<div class="portfolio-item">
									<img src="img/portfolio/09.jpg" alt="" />
									<div class="portfolio-desc align-center">
										<div class="folio-info">
											<a href="img/portfolio/09.jpg" class="fancybox"><i
												class="fa fa-plus fa-2x"></i></a>
										</div>
									</div>
								</div>
							</article>
						</div>
					</div>
				</div>
			</div>

		</div>
	</section>
	<section id="parallax2" class="section parallax"
		data-stellar-background-ratio="0.5">
		<div class="align-center pad-top40 pad-bot40">
			<blockquote class="bigquote color-white">程序员的自我修养</blockquote>
			<p class="color-white">mathwebteam</p>
		</div>
	</section>

	<!-- contact -->
	<section id="section-contact" class="section appear clearfix">
		<div class="container">

			<div class="row mar-bot40">
				<div class="col-md-offset-3 col-md-6">
					<div class="section-header">
						<h2 class="section-heading animated" data-animation="bounceInUp">联系我们</h2>
						<p style="color: green">你的到来是我们的荣幸，请让我们和你一瞻你的才华和魅力</p>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-8 col-md-offset-2">
					<div class="cform" id="contact-form" style="color: black">
						<form action="/Teamintro/DealIndex" method="post">
							<div class="form-group">
								<label for="name">您的姓名</label> <input type="text" name="name"
									class="form-control" id="name" placeholder="您的姓名"
									data-rule="minlen:2" data-msg="请填写您的姓名" />
								<div class="validation"></div>
							</div>
							<div class="form-group">
								<label for="email">邮箱</label> <input type="email"
									class="form-control" name="email" id="email" placeholder="你的邮箱"
									data-rule="email" data-msg="请填写您的邮箱，以便我们对您的及时回复" />
								<div class="validation"></div>
							</div>
							<div class="form-group">
								<label for="subject">介绍</label> <input type="text"
									class="form-control" name="subject" id="subject"
									placeholder="介绍" data-rule="minlen:2" data-msg="用一句话介绍你自己" />
								<div class="validation"></div>
							</div>
							<div class="form-group">
								<label for="message">留言</label>
								<textarea class="form-control" name="message" rows="5"
									placeholder="留言给我们" data-rule="required" data-msg="求你给我们的留言"></textarea>
								<div class="validation"></div>
							</div>
							<button id="button" type="submit" class="btn btn-theme pull-left">发送</button>
							<br />
							<div id="sendmessage">您的信息已发送，我们会尽快回复您，谢谢！</div>
						</form>
						<h4><a class="btn btn-primary" href="/home/melody/报名表.doc" role="button">下载报名表</a></h4>
					</div>
				</div>
				<!-- ./span12 -->
			</div>

		</div>
	</section>
	<section id="footer" class="section footer">
		<div class="container">
			<div class="row align-center copyright">
				<div class="col-sm-12">
					<p>Copyright &copy; 2014 MathWebTeam</p>
					<p>
						欢迎访问，您是第<%=tNum%>个访问的有志青年！
					</p>
				</div>
			</div>
		</div>
	</section>
	<script src="js/modernizr-2.6.2-respond-1.1.0.min.js"></script>
	<script src="js/jquery.js"></script>
	<script src="js/jquery.easing.1.3.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.isotope.min.js"></script>
	<script src="js/jquery.nicescroll.min.js"></script>
	<script src="js/fancybox/jquery.fancybox.pack.js"></script>
	<script src="js/skrollr.min.js"></script>
	<script src="js/jquery.scrollTo-1.4.3.1-min.js"></script>
	<script src="js/jquery.localscroll-1.2.7-min.js"></script>
	<script src="js/stellar.js"></script>
	<script src="js/jquery.appear.js"></script>
	<script src="js/validate.js"></script>
	<script src="js/main.js"></script>
</body>
</html>

