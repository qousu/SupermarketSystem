$(document).ready(function() {
    // 初始化工具提示
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl, {
            opacity: 0.5  // 设置提示信息的透明度为50%
        });
    });

    // 商品分类下拉菜单悬停效果
    const categoryMenu = {
        $trigger: $('.dropdown-hover'),
        $submenu: $('#categorySubmenu'),
        $popup: $('.category-popup'),
        $chevron: $('.dropdown-hover .bi-chevron-down'),
        $mainContent: $('#main-content'),
        isLocked: false,
        hoverTimeout: null
    };

    // 鼠标进入时展开菜单
    categoryMenu.$trigger.hover(
        function() {
            if (!$('#sidebar').hasClass('collapsed')) {
                categoryMenu.$submenu.addClass('show');
                categoryMenu.$chevron.css('transform', 'rotate(-180deg)');
            }
        },
        function() {
            if (!categoryMenu.isLocked) {
                categoryMenu.hoverTimeout = setTimeout(() => {
                    categoryMenu.$submenu.removeClass('show');
                    categoryMenu.$chevron.css('transform', 'rotate(0deg)');
                }, 2000);
            }
        }
    );

    // 点击处理
    categoryMenu.$trigger.click(function(e) {
        e.preventDefault();
        e.stopPropagation();

        if ($('#sidebar').hasClass('collapsed')) {
            // 侧边栏收起时，显示右侧弹出列表
            categoryMenu.$popup.toggleClass('show');

            // 切换主内容区域的状态
            if (categoryMenu.$popup.hasClass('show')) {
                categoryMenu.$mainContent.addClass('category-expanded');
            } else {
                categoryMenu.$mainContent.removeClass('category-expanded');
            }
        } else {
            // 侧边栏展开时，切换下拉菜单
            categoryMenu.isLocked = !categoryMenu.isLocked;

            if (categoryMenu.isLocked) {
                categoryMenu.$submenu.addClass('show');
                categoryMenu.$chevron.css('transform', 'rotate(-180deg)');
            } else {
                categoryMenu.$submenu.removeClass('show');
                categoryMenu.$chevron.css('transform', 'rotate(0deg)');
            }
        }
    });

    // 点击页面其他地方关闭弹出列表
    $(document).click(function(e) {
        if (!$(e.target).closest('.category-popup, .dropdown-hover').length) {
            categoryMenu.$popup.removeClass('show');
            categoryMenu.$mainContent.removeClass('category-expanded');
        }
    });

    // 鼠标进入子菜单时清除关闭定时器
    categoryMenu.$submenu.hover(
        function() {
            if (categoryMenu.hoverTimeout) {
                clearTimeout(categoryMenu.hoverTimeout);
            }
        },
        function() {
            if (!categoryMenu.isLocked) {
                categoryMenu.hoverTimeout = setTimeout(() => {
                    categoryMenu.$submenu.removeClass('show');
                    categoryMenu.$chevron.css('transform', 'rotate(0deg)');
                }, 2000);
            }
        }
    );

    // 侧边栏收起时重置菜单状态
    function resetCategoryMenu() {
        categoryMenu.$submenu.removeClass('show');
        categoryMenu.$popup.removeClass('show');
        categoryMenu.$mainContent.removeClass('category-expanded');
        categoryMenu.$chevron.css('transform', 'rotate(0deg)');
        categoryMenu.isLocked = false;
        if (categoryMenu.hoverTimeout) {
            clearTimeout(categoryMenu.hoverTimeout);
        }
    }

    // 在侧边栏收起时重置菜单
    $('#sidebarToggle').click(function() {
        resetCategoryMenu();
    });

    // 在窗口大小改变时重置菜单
    $(window).resize(function() {
        if ($(window).width() <= 768) {
            resetCategoryMenu();
        }
    });

    // 用户头像下拉菜单交互 - 完全重写
    const userDropdown = {
        $container: $('.user-dropdown'),
        $menu: $('.user-dropdown .dropdown-menu'),
        $wrapper: $('.user-dropdown-wrapper'),
        isMenuOpen: false,
        menuTimeout: null,

        init: function() {
            // 鼠标进入下拉区域
            this.$container.on('mouseenter', () => {
                clearTimeout(this.menuTimeout);
                this.showMenu();
            });

            // 鼠标离开下拉区域
            this.$container.on('mouseleave', () => {
                // 延迟关闭，给用户时间移动到菜单
                this.menuTimeout = setTimeout(() => {
                    this.hideMenu();
                }, 300);
            });

            // 鼠标离开菜单后再次检查
            this.$menu.on('mouseleave', () => {
                if (!this.$container.is(':hover')) {
                    this.menuTimeout = setTimeout(() => {
                        this.hideMenu();
                    }, 300);
                }
            });

            // 点击头像时切换菜单状态
            $('#userDropdown').on('click', (e) => {
                e.preventDefault();
                if (this.isMenuOpen) {
                    this.hideMenu();
                } else {
                    this.showMenu();
                }
            });

            // 点击菜单项时关闭菜单
            this.$menu.find('.dropdown-item').on('click', () => {
                this.hideMenu();
            });

            // 点击页面其他区域时关闭菜单
            $(document).on('click', (e) => {
                if (!this.$container.is(e.target) &&
                    this.$container.has(e.target).length === 0) {
                    this.hideMenu();
                }
            });

            return this;
        },

        showMenu: function() {
            this.$menu.addClass('show');
            this.isMenuOpen = true;
        },

        hideMenu: function() {
            this.$menu.removeClass('show');
            this.isMenuOpen = false;
        }
    };

    // 初始化用户下拉菜单
    userDropdown.init();

    // 侧边栏展开/收起状态控制 - 优化版本
    function toggleSidebar() {
        const $sidebar = $('#sidebar');
        const $mainContent = $('#main-content');
        const isCollapsed = $sidebar.hasClass('collapsed');

        // 切换侧边栏状态
        $sidebar.toggleClass('collapsed');
        $mainContent.toggleClass('expanded');

        // 更新切换按钮图标方向
        if ($sidebar.hasClass('collapsed')) {
            // 收起状态
            $('#sidebarToggle i').removeClass('bi-chevron-left').addClass('bi-chevron-right');

            // 启用工具提示
            tooltipList.forEach(tooltip => tooltip.enable());

            // 保存状态到本地存储
            localStorage.setItem('sidebarCollapsed', 'true');

            // 小屏幕处理
            if ($(window).width() <= 768) {
                $sidebar.removeClass('active');
            }
        } else {
            // 展开状态
            $('#sidebarToggle i').removeClass('bi-chevron-right').addClass('bi-chevron-left');

            // 禁用工具提示
            tooltipList.forEach(tooltip => tooltip.disable());

            // 保存状态到本地存储
            localStorage.setItem('sidebarCollapsed', 'false');

            // 小屏幕处理
            if ($(window).width() <= 768) {
                $sidebar.addClass('active');
            }
        }
    }

    // 侧边栏标题点击也可以触发收起/展开
    $('.sidebar-header').click(function() {
        toggleSidebar();
    });

    // 导航栏中的侧边栏切换按钮（在小屏幕上显示）
    $('#navSidebarToggle').click(function() {
        const $sidebar = $('#sidebar');

        if ($sidebar.hasClass('collapsed')) {
            // 如果侧边栏已收起，则先展开再显示
            $sidebar.removeClass('collapsed');
            $('#sidebarToggle i').removeClass('bi-chevron-right').addClass('bi-chevron-left');

            // 禁用工具提示
            tooltipList.forEach(tooltip => tooltip.disable());

            // 更新本地存储状态
            localStorage.setItem('sidebarCollapsed', 'false');
        }

        // 切换active类来显示/隐藏侧边栏
        $sidebar.toggleClass('active');
    });


    // 自动处理响应式布局切换
    $(window).resize(function() {
        const $sidebar = $('#sidebar');
        const windowWidth = $(window).width();

        if (windowWidth > 768) {
            // 大屏幕
            $sidebar.removeClass('active');
        } else {
            // 小屏幕
            if (!$sidebar.hasClass('active')) {
                $sidebar.addClass('collapsed');
            }
        }
    });

    // 页面加载时恢复侧边栏状态
    function restoreSidebarState() {
        const $sidebar = $('#sidebar');
        const $mainContent = $('#main-content');
        const sidebarState = localStorage.getItem('sidebarCollapsed');
        const windowWidth = $(window).width();

        // 如果是第一次访问，设置默认状态
        if (sidebarState === null) {
            if (windowWidth <= 768) {
                // 小屏幕默认收起
                $sidebar.addClass('collapsed');
                $mainContent.addClass('expanded');
                localStorage.setItem('sidebarCollapsed', 'true');
            } else {
                // 大屏幕默认展开
                localStorage.setItem('sidebarCollapsed', 'false');
            }
            return;
        }

        if (sidebarState === 'true') {
            // 如果保存的状态是收起的，则收起侧边栏
            if (!$sidebar.hasClass('collapsed')) {
                $sidebar.addClass('collapsed');
                $mainContent.addClass('expanded');
                $('#sidebarToggle i').removeClass('bi-chevron-left').addClass('bi-chevron-right');
                tooltipList.forEach(tooltip => tooltip.enable());
            }
        } else {
            // 如果保存的状态是展开的，则确保侧边栏展开
            $sidebar.removeClass('collapsed');
            $mainContent.removeClass('expanded');
            $('#sidebarToggle i').removeClass('bi-chevron-right').addClass('bi-chevron-left');

            // 小屏幕处理
            if (windowWidth <= 768) {
                $sidebar.addClass('active');
            }
        }
    }

    // 页面加载时调用
    restoreSidebarState();
});
