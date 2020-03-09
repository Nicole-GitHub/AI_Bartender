<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
			</div>
		</div>
		<div class="footer">
			<div class="footerTop"></div>
			<div style="height:7vh" ></div>
		</div>
	</body>
	
    <script type="text/javascript">

		function windowResize(bodyh){
			bodyh += 50;
			var vh = $(window).height();
			var headerh = $(".header").height();
			var footerh = $(".footer").height();
			if (bodyh < vh - headerh - footerh) {
				$(".footer").addClass("positionFix");
				$(".bodyRight").height(vh - headerh - footerh);
			}else{
				$(".footer").removeClass("positionFix");
				$(".bodyRight").height(bodyh);
			}
		}
	</script>
</html>