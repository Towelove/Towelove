module.exports = {
    title: 'Towelove',
    description: '双向的爱',
    themeConfig: {
      search: true,
      searchMaxSuggestions: 10,
      //首页的右上角的导航栏
      nav: [
        { text: '主页', link: '/' },
        { text: '使用', link: '/useage.html' },
        { text: 'CSDN', link: 'https://blog.csdn.net/Zhangsama1?type=bbs' },
        { text: 'Github', link: 'https://github.com/Towelove' }
      ],
      //对应的是侧边的导航栏
      sidebar: [
        {
          title: '产品文档',   // 必要的
          path: '/prd.html',      // 可选的, 标题的跳转链接，应为绝对路径且必须存在
          sidebarDepth: 1,    // 可选的, 默认值是 1
          // children: [
          //   '/'
          // ]
        },
        {
          title: '梦开始的地方',   // 必要的
          path: '/origin.html',      // 可选的, 标题的跳转链接，应为绝对路径且必须存在
          sidebarDepth: 1,    // 可选的, 默认值是 1
          // children: [
          //   '/'
          // ]
        },
        {
          title: '项目介绍',
          path: '/introduce.html',
          // children: [ /* ... */ ],
          // initialOpenGroupIndex: -1 // 可选的, 默认值是 0
        },
        {
          title: '项目技术栈及版本',   // 必要的
          path: '/techstack.html',      // 可选的, 标题的跳转链接，应为绝对路径且必须存在
          sidebarDepth: 1,    // 可选的, 默认值是 1
          // children: [
          //   '/'
          // ]
        },
        {
          title: '技术Tips',   // 必要的
          path: '/tips.html',      // 可选的, 标题的跳转链接，应为绝对路径且必须存在
          sidebarDepth: 1,    // 可选的, 默认值是 1
          // children: [
          //   '/'
          // ]
        },
        {
          title: '项目开发工具',   // 必要的
          path: '/devtools.html',      // 可选的, 标题的跳转链接，应为绝对路径且必须存在
          sidebarDepth: 1,    // 可选的, 默认值是 1
          // children: [
          //   '/'
          // ]
        },
        {
          title: '遇到的问题及解决办法',   // 必要的
          path: '/p&s.html',      // 可选的, 标题的跳转链接，应为绝对路径且必须存在
          sidebarDepth: 1,    // 可选的, 默认值是 1
          // children: [
          //   '/'
          // ]
        },
        {
          title: '调优策略',   // 必要的
          path: '/optimize.html',      // 可选的, 标题的跳转链接，应为绝对路径且必须存在
          sidebarDepth: 1,    // 可选的, 默认值是 1
          // children: [
          //   '/'
          // ]
        },
      ]
    }

  }

