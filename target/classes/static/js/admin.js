$(document).ready(function () {
    let timeoutId;

    // 商品分类下拉菜单鼠标移入事件
    $('.nav-item.dropdown').on('mouseenter', function () {
        clearTimeout(timeoutId);
        $(this).addClass('show');
    });

    // 商品分类下拉菜单鼠标移出事件
    $('.nav-item.dropdown').on('mouseleave', function () {
        timeoutId = setTimeout(() => {
            $(this).removeClass('show');
        }, 200);
    });

    // 用户头像下拉菜单鼠标移入事件
    $('.user-dropdown').on('mouseenter', function () {
        clearTimeout(timeoutId);
        $(this).addClass('show');
    });

    // 用户头像下拉菜单鼠标移出事件
    $('.user-dropdown').on('mouseleave', function () {
        timeoutId = setTimeout(() => {
            $(this).removeClass('show');
        }, 200);
    });

    // 下拉菜单项点击事件
    $('.dropdown-item').on('click', function (e) {
        e.preventDefault();
        const href = $(this).attr('href');
        if (href) {
            window.location.href = href;
        }
    });

    // 添加菜单项动画延迟
    $('.dropdown-item').each(function (index) {
        $(this).css('animation-delay', (index * 0.05) + 's');
    });
});
