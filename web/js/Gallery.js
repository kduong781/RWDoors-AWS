/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var main = function() {
    $(".deleteImage").click(          
          function(){
              //alert($(this).attr("id"));
             $('.deleteImage').removeClass('delete');
             $(this).toggleClass('delete');
             $("#deleteImageID").html($(this).attr("id"));
             $("#deleteImageID").val($(this).attr("id"));
      });

}
$(document).ready(main);