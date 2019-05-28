import 'expose-loader?$!expose-loader?jQuery!jquery'
import '@/assets/js/jquery.bxslider/jquery.bxslider';
export default {
    main : function(){
        $(function() {
            setTimeout(function () {
                $('.visual-slide li').eq(1).addClass('active');
            }, 1000);
            let visualSlide = $('.visual-slide').bxSlider({
                auto: true,
                mode: 'vertical',
                mouseDrag: true,
                speed: 1000,
                pause: 8000,
                pager: true,
                touchEnabled: true,
                controls: false,
                autoControls: false,
                autoControlsCombine: true,
                onSlideAfter: function ($slideElement, oldIndex, newIndex) {
                    $('.visual-slide li').removeClass('active');
                    $('.visual-slide li').eq(newIndex + 1).addClass('active');
                    let current = visualSlide.getCurrentSlide();
                    let slideQty = visualSlide.getSlideCount();

                    $('span.numerhere').html(current + 1);
                    $('span.totalnumer').html(slideQty);
                }
            });
            let slideeQQ = visualSlide.getSlideCount();
            $('span.ltest').html(slideeQQ);

            let $contentTop = $(".section-01").offset().top;

            $(".scroll-btn").on("click", function () {
                $('html, body').stop().animate({scrollTop: $contentTop});
            });


            tab(".tab-tit > li > a", " .tab-con", "active", 0);

            function tab(selecter, selcontent, selactive, index) {

                let $selecter = null;
                let $selcontent = null;
                let active = null;
                let conIndex = null;

                function start() {
                    init();
                    initEvent();
                }

                function init() {
                    $selecter = $(selecter);
                    $selcontent = $(selcontent);
                    active = selactive;
                    conIndex = index;
                }

                function initEvent() {
                    //$selcontent.hide();
                    // $selcontent.eq(conIndex).show();

                    $selecter.click(function (e) {
                        e.preventDefault();
                        $selecter.removeClass(active);
                        $(this).addClass(active);
                        $selcontent.removeClass("active");
                        let activeTab = $(this).attr("href");
                        $(activeTab).addClass(active);
                    });
                }

                start();
            };
        });
    },
    slide : function(){
        $(function(){
            $('.gallery-list').bxSlider({
                speed: 1000,
                pause: 8000,
                minSlides: 10,
                maxSlides: 10,
                moveSlides: 1,
                slideWidth: 330,
                slideMargin: 15,
                pager: true,
                controls: false,
                auto: true,
                responsive: true,
                touchEnabled: false,
                swipeThreshold: 50
            });
        })

    }
}