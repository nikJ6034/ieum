// ;(function($, window, document, undefined) {


    //fullMenu(".menu > li",".menu > li  > a",".menu > li > .snb1",".gnb-area");


   /* var $body = $("body");
    $moHeadr = $(".mo-menu");
    $moBak = $(".bodyBg");

    $(".mo-menu-btn").on("click", function () {
        if($(this).hasClass("action")){
            $(this).removeClass("action")
            $body.removeClass("action");
            $moHeadr.removeClass("action");
            $moBak.removeClass("action");
        }else{
            $(this).addClass("action");
            $body.addClass("action");
            $moBak.addClass("action");
            $moHeadr.addClass("action");
        }

    });

    $(".top-up-btn").on("click", function(){
        $('html, body').stop().animate( { scrollTop : '0' } );
    });
*/



// })(window.jQuery, window, document);

export default {
    fullMenu : function(selecter,selecter2,item,h_wrap){
                let $selecter = null;
                let $selecter2 = null;
                let $item = null;
                let $h_wrap = null;
                let $h_wrapH = null;

               function start(){
                  // init();
                   initEvent();
               }

               function initEvent() {

                   $selecter = $(selecter);
                   $selecter2 = $(selecter2);
                   $item = $(item);
                   $h_wrap = $(h_wrap);
                   $h_wrapH = $(h_wrap).outerHeight();

                   let plug = false;

                   $selecter.on("mouseenter focusin",function(){
                       open();
                   });

                   $(".sitemap").on("click",function(){
                       open();
                   });


                   $h_wrap.on("mouseleave",function(){
                       if(plug === false){ return false; }
                       close();

                   });

                   function open(){  // 메뉴오픈

                       let idx_h = $(item).outerHeight()+$h_wrapH;

                       $h_wrap.stop(true,false).animate({"height":idx_h},300);

                       $item.show();

                       $h_wrap.addClass("action");

                       plug = true;

                       return true;
                   };

                   function close(){ // 메뉴닫기
                       $h_wrap.stop(true,false).animate({"height":"90px"},300);
                       $item.hide();
                       $h_wrap.removeClass("action");

                       plug = false;

                       return true;
                   };

               }
               start();

       },
    accordionMenu : function (selecter, active) {

        var $selecter = $(selecter);
        var $active = $(active);

        function start() {
            initEvent();
            initActive();
        }

        function initEvent() {
            $selecter.on('click', function (e) {
                if($(this).find("ul > li").length > 0){
                    e.preventDefault();
                }
                setSeleterItem($(this));
            });
            $selecter.find("ul > li").on('click', function (e) {
                e.stopPropagation();
            });

        }
        function setSeleterItem(seleter) {
            var $item = seleter.find("ul");

            $selecter.find("ul").slideUp();
            $selecter.removeClass("active");

            if ($item.css("display") == "none") {
                seleter.addClass("active");
                $item.slideDown("slow");
            } else {
                seleter.removeClass("active");
                $item.slideUp("slow");
            }
        }
        function initActive() {
            if ($selecter.hasClass("active") == true) {
                $active.find("ul").slideDown();
            };
        }
        start();
    },
    mobileMenu : function(){
        let $body = $("body");
        let $moHeadr = $(".mo-menu");
        let $moBak = $(".bodyBg");
        $body.removeClass("action");
        $(".mo-menu-btn").on("click", function () {
            if($(this).hasClass("action")){
                $(this).removeClass("action")
                $body.removeClass("action");
                $moHeadr.removeClass("action");
                $moBak.removeClass("action");
            }else{
                $(this).addClass("action");
                $body.addClass("action");
                $moBak.addClass("action");
                $moHeadr.addClass("action");
            }

        });
    }
}