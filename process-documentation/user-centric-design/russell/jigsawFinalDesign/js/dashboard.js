(function() {


  $( document ).ready(function() {
    $("#subscribeButton").click(function(){
    	$("#curtain").fadeIn();
      $("#curtain").css("position","fixed");
      $("#curtain").css("top","0px");
      $("#curtain").css("left","0px");
      $("#curtain").css("margin","0px");
      $("#curtain").css("width","100%");
      $("#subscribePopup").fadeIn();	
      $("#subscribePopup").css("position","absolute");
      $("#subscribePopup").css("top","100px");
      $("#subscribePopup").css("margin","0 auto");
      $("#subscribePopup").css("margin-left","10%");
      $("#subscribePopup").css("width","70%");
    }); 
    $("#settingsButton").click(function(){
    	$("#curtain").fadeIn();
      $("#curtain").css("position","fixed");
      $("#curtain").css("top","0px");
       $("#curtain").css("left","0px");
      $("#curtain").css("margin","0px");
      $("#curtain").css("width","100%");
      $("#settingsPopup").fadeIn();
      $("#settingsPopup").css("position","absolute");
      $("#settingsPopup").css("top","100px");
        $("#settingsPopup").css("margin","0 auto");
      $("#settingsPopup").css("margin-left","10%");
          
      $("#settingsPopup").css("width","70%");
    }); 
    $("#searchButton").click(function(){
    $("#curtain").fadeIn();
      $("#curtain").css("position","fixed");
      $("#curtain").css("top","0px");
       $("#curtain").css("left","0px");
      $("#curtain").css("margin","0px");
      $("#curtain").css("width","100%");
      $("#searchPopup").fadeIn();	
      $("#searchPopup").css("position","absolute");
      $("#searchPopup").css("top","100px");
        $("#searchPopup").css("margin","0 auto");
      $("#searchPopup").css("margin-left","10%");
      $("#searchPopup").css("width","70%");
    }); 
    
    $("#subscribeClose").click(function(){
    	$("#subscribePopup").fadeOut();	
      $("#curtain").fadeOut();
    }); 
    $("#settingsClose").click(function(){
    	$("#settingsPopup").fadeOut();
      $("#curtain").fadeOut();
    }); 
    $("#searchClose").click(function(){
    	$("#searchPopup").fadeOut();	
      $("#curtain").fadeOut();
    }); 
    
    $("#listItem").click(function(){
      $("#tableList").css("margin-left","75px");
      $("#tableListInner").css("margin-top","-123px");
      $("#tableDetails").show();
      $("#returnToMap").show();
      $("#tableDetails").css("margin-left","395px");
      $("#map").hide();
    }); 
    $("#returnToMap").click(function(){
      $("#tableList").css("margin-left","20px");
      $("#tableListInner").css("margin-top","10px");
      $("#tableDetails").hide();
      $("#returnToMap").hide();
      $("#map").show();
    }); 
  });
})();