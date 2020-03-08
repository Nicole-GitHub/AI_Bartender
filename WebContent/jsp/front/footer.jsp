<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
        <div class="footer">
            <div class="WarningSign">未滿十八歲者，禁止飲酒</div>
            <div class="copyright"><p>copyright © 2020 AI-sommelier all rights reserved.</p></div>
        </div>
    </body>
    <script type="text/javascript">

		function windowResize(bodyh){
			var vh = $(window).height();
			var headerh = $(".header").height();
			var footerh = $(".footer").height();
			if (bodyh < vh - headerh - footerh) {
				$(".footer").addClass("positionFix");
			}else{
				$(".footer").removeClass("positionFix");
			}
		}
	</script>
</html>