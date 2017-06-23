<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index</title>
</head>
<body>
<div>
	<div id="flowimage">
	</div>
</div>

<script src="${pageContext.request.contextPath }/script/jquery/jquery-1.12.4.min.js"></script>
<script type="text/javascript">

	console.log("jquery out");

	$(document).ready(function() {
		console.log("jquery in");
		
		var flowimage = $('#flowimage');  
		loadProcessImage(flowimage);  
		
		$("#submit").bind("click", function() {
			console.log("click");
			return true;
		});
		
	});
	
	function loadProcessImage(flowimage){  
        var imageUrl = '${pageContext.request.contextPath }/activitiServlet';  
        // 加载图片  
        $('<img />',{
            "src" : imageUrl,  
            "alt" : '' ,
            "id": 'img-id'
        }).appendTo(flowimage);   
        
        console.log( $("#img-id").width() + ", " + $("#img-id").height() );
        console.log($("#img-id").position().top + ", " + $("#img-id").position().left);
        console.log($("#img-id").offset().top + ", " + $("#img-id").offset().left);
    }  
	
</script>
</body>
</html>