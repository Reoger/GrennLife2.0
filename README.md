# GrennLife
项目开工啦 基于MVP架构，目前实现了登陆注册
主要功能
1. 注册、登陆、个人信息
2. 进行资源回收（类似于矿泉水瓶的回收）
3. 物品重复利用（类似于图书漂流）
3. 监督举报危害环境的行为（通过上传文字、位置信息、图片等相关信息进行举报）
4. 动态（包括发布、评论、点赞等功能）
5. 环保知识(每天及时跟新与环保相关的知识、提高用户的环保知识)

关于本作品视频介绍，请点[这里](http://ofsm19b46.bkt.clouddn.com/2016-10-31_19_39_56.mp4)
如果想下载体验本作品，请点[这里](http://ofsm19b46.bkt.clouddn.com/greenLife.apk)。

## 目标群体
本作品的主要应用人群是广大的青少年用户，另外能接触到智能手机（自己拥有或亲朋好友拥有）并且有着环保意识的社会人群都是我们潜在的目标客户。旨在提高用户的环保意识，添加用户环境保护的理念和环境保护的知识。本作品通过提供用户举报危害环境行为提高用户参与环境保护的积极性，通过资源回收提高资源的利用率，通过建立绿色圈来吸引用户长期保持活跃状态。
重点可发展的目标人群还包括社区居民，根据调研，发现城市居民普遍存在这个矛盾，由于日常产生的废品多，但限于房子小，废品体积大，价格不高，卖废品难度大的问题而无奈地选择丢弃废品。而本公司垃圾分类回收项目能很好调动居民分类垃圾的热情，通过移动手机扫扫二维码即可轻松实现分类垃圾赚积分，用积分换物品。这种既可以随时随地卖废品赚取额外收入，又可以清洁房子节省空间的废品回收体验一定能吸引社区居民尤其是家庭主妇的青睐。
根据本项目发展，目前我们的首要目标人群是最为前卫，环保意识最为突出的大学生群体 ：第一，我们本身就是大学生，对于大学生处理寝室垃圾的烦恼感同身受，大量堆积的分类垃圾侵占狭小的寝室空间，而小量的分类垃圾又投送无门；第二，大学生时间相对宽裕，能做好垃圾分类，同时根据我们实地调研，。他们对于我们的项目接受度很高，推广难度较小；第三，大学生接受新事物的能力更强，因此环保百科、环保科技等相关知识更容易普及；第四，大学生的社交需求也更大，而该APP包含动态圈功能，容易让大学生用户保持活跃

总体功能结构
 
![功能结构图.png](http://upload-images.jianshu.io/upload_images/2178834-593b26c9e217a27e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 具体功能模块设计 
本作品基于“绿色环保”的理念，并以回收利用旧物和看似无用的垃圾为突破点，独具匠心的设计了以下几个重要的功能。
##  注册、登陆
作为一个手机应用软件，完善的个人信息是很基础，并且重要的。我们实现了注册，登录，重置密码以及认证成为垃圾回收者等完善的个人信息功能。我们实现了自动登录，重置密码功能。
##  资源利用
在资源在利用中，主要由两大功能组成，一个是垃圾的回收，另一个就是旧物的回收利用。
垃圾回收主要 实现了帮助普通用户找到附近可以回收垃圾的回收垃圾的人的功能，方便用户处理还存在利用价值的垃圾，例如易拉罐，旧铁、铝制品等等。对于垃圾回收者来说，可以很方面的回收附近存在的可以回收垃圾的户，提高回收垃圾的效率。
旧物利用功能是基于“图书漂流”的思想。当用户有不需要的旧物，但是又不想当作垃圾处理，可以将其发布到我们的app中。通过app其他 用户可以很方便的看到用户发布的资源信息，当用户对其发布的资源感兴趣时，可以与其发布者直接联系，与其协商资源的相关信息。与二手市场不同的是，我们这个旧物利用完全是，没有明码标价的行为的，我们只是提供一个让他们获取相关信息的 渠道，并且我们提倡的是完全免费。
##  监督功能
相信不少人都会遇到有污染环境的行为，当我们想去相关部门反映时，却发现自己不知道“相关部门“究竟是那个部门。于是我们就想到我们自己做一个app，来收集这些反映污染的信息。我们收集到相关信息之后，如果信息属实的话，我们就举报信息投递到相关部门的邮箱中。后续我们会主动推送相关信息告知用户举报进度。如此，我们的用户就可以很方便的实现举报污染环境的行为，而不需要经过繁琐的步骤。通过我们的环境监督功能，传播”人人都是监督员，人人都是环保员“的理念，以提高我们的环保理念和参与环保的意识。
##  环保圈
环保圈功能就是可以发布自己的对环保的一些心得体会，环保小技巧或者是自己此时想说的话。发布发出后，其他用户就可以看到这些记录，并且可以为起点赞，评论或者分享，通过该功能可以营造一种环境保护的良好氛围。
##  设置
在设置选项中，我们添加了app常有的一些设置属性，例如清除缓存，更新设置等等选项，为用户使用提供了更多的设置选项，以提高用户体验度。

# 界面设计
用户界面是程序中用户能看见并与之交互作用的部分,设计一个好的用户界面是非常重要的,本设计将为用户提供美观,大方,直观,操作简单的具备android风格的用户界面。
项目UI风格：以白色、绿色、黑色为基色的简朴风格，同时增加了些许当前安卓软件包含的流行因素。
            
## 主界面
采用底部的小标题栏进行三个主要界面的流畅切换，既保证了美观，又保证了用户体验的实用性；
主页以灰白为背景，上部分为轮播图（与环保相关联的图片进行轮流播放），下部分排布六个功能按钮；
动态圈由很多小item组成，包括发布者信息、内容标题和详情，以及点赞（喜欢）、分享、评论等；发布动态圈按钮为半透明的悬浮按钮，较美观；
个人中心界面分隔成三个小部分：
（1）用户的基本资料；
（2）详细资料、我的监控历史、资源、与我相关；
（3）APP设置，分享APP，反馈；
整体来说界面简洁而方面操作。

![主页界面.png](http://upload-images.jianshu.io/upload_images/2178834-6058bfaefad826a3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![动态圈界面.png](http://upload-images.jianshu.io/upload_images/2178834-52f37450ed773967.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 
![个人中心界面](http://upload-images.jianshu.io/upload_images/2178834-103912bbee77017d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##  登录、注册、重置密码界面
三个界面采用同一背景图片，风格统一；输入框添加图标提示，整体布局大方素雅，没有多余的视觉障碍。
   
![登录界面.png](http://upload-images.jianshu.io/upload_images/2178834-49dab8b12feb9190.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![注册界面.png](http://upload-images.jianshu.io/upload_images/2178834-b825a459852f36da.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
 
![ 重置密码界面.png](http://upload-images.jianshu.io/upload_images/2178834-bd480a9d4e14fe6a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##  功能界面
资源回收界面利用顶部的滑动条将主要的两个功能界面展示出来，最大程度上节省了对屏幕空间的利用；
垃圾回收界面包含所有已认证垃圾回收者的准确信息，旧物利用有相关物品的图片、准确定位、联系方式等，简单明了，用户容易抓住重点。
    
![垃圾回收.png](http://upload-images.jianshu.io/upload_images/2178834-8ac9501adce5ff25.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![旧物利用.png](http://upload-images.jianshu.io/upload_images/2178834-0ed40fef17fb2b76.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

环保新闻、环保科技、法律法规、环保百科四个界面都采用卡片设计布局，文章以类似卡片的样式分隔开。
      
![ 环保新闻 .png](http://upload-images.jianshu.io/upload_images/2178834-87d41db981912e9b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![ 环保科技.png](http://upload-images.jianshu.io/upload_images/2178834-ce0bf9e17706deb7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
       

![ 法律法规.png](http://upload-images.jianshu.io/upload_images/2178834-ee040b980b1bfdfb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![环保百科.png](http://upload-images.jianshu.io/upload_images/2178834-315a7e45428df556.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![环境监控.png](http://upload-images.jianshu.io/upload_images/2178834-94df88569ff4495b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
 
## 个人界面、设置界面
个人界面包括垃圾回收者的认证;
       
![详细信息.png](http://upload-images.jianshu.io/upload_images/2178834-4c7cd236d81daa3e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![ 设置.png](http://upload-images.jianshu.io/upload_images/2178834-6d2ef8c873a7bad3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![反馈界面.png](http://upload-images.jianshu.io/upload_images/2178834-b0762464c62e1efe.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![版本更新界面.png](http://upload-images.jianshu.io/upload_images/2178834-0f69dfe9ea685d17.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

动态圈详情界面，评论界面；应用分享：能分享到目前的主流通信软件，例如：微信、QQ等。
   
![动态圈评论.png](http://upload-images.jianshu.io/upload_images/2178834-404ef1c520e7ac6e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![APP分享.png](http://upload-images.jianshu.io/upload_images/2178834-d8303b1989569cdd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 作品实现、特色和难点
## 作品实现
本作品是基于android 原生态开发，开发工具是android studio 2.1。开发的主要语言为java，本作品实用了目前比较火的MVP框架来实现整体逻辑，项目整体的构架图如下：
 
![整体构架图.png](http://upload-images.jianshu.io/upload_images/2178834-79fc1475a1dc23b4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 登陆，注册
利用bmob后端云提供的短信验证sdk，用户可以凭借手机号码获取一个验证码，正确填写好验证码之后便能创建一个账号。用户可以在登录成功之后，可以修改自己的个人信息。
## 环境监督
通过绑定已经登录的个人信息，自动获取当前位置信息并填写位置信息，用户手动填写举报的标题和具体内容，有必要的话，用户还可以添加不超过9张的图片，将上述信息一并上传到我们的服务端后台，我们收到举报信息之后，核查之后就将起举报信息分发到处理该信息的有关部门，及时提醒他们环境问题。当我们 受理了用户的举报信息之后，会主动给用户发送举报的进度和 执行情况。
##  垃圾回收、旧物利用
首先需要收集用户发布的资源信息，收集的信息包括：资源标题，资源描述，位置信息和基本的个人信息。用户发布信息之后，app自动加载显示出来，便于其他用户查看。
1）垃圾回收
当用户认证成为垃圾回收者，并且在后台审核通过之后，我们就能在垃圾回收者列表中看到他的信息，并且可以看到彼此之间的距离，如果有必要，可以直接通过电话联系到垃圾回收者，进而实现垃圾回收。
2）旧物利用
首先需要收集用户发布的资源信息，收集的信息包括：资源标题，资源描述，位置信息和基本的个人信息。用户发布信息之后，app自动加载显示出来，便于其他用户查看。如果有必要，可以直接通过发布资源时留下的电话号码来进行联系，进而实现旧物利用。当用户发布的资源已经被人领取之后，我们可以在自己的个人资源信息界面将资源的状态设置为已经领取。
## 环保百科、环保新闻、环保资讯
环保百科我们采用了bmob后端云数据库作为后端数据来源，手动添加比较实用的环保百科知识到数据库中，确保用户能看到正确且实用的小知识。在手机加载方面，我们实现了缓存到手机本地数据库中，只有第一次加载需要从后端获取，再次加载会先加载本地数据库中的内容，并查询最新数据更新到本地，进而提高访问速度。
## 个人设置
个人设置实现了不多数app都实现的功能，其中包括：版本更新，清除缓存，应用分享等等。版本更新采用的是bmob的sdk，应用分享实现了分享到主流通讯软的功能，集成了shareSDK。后面将有详细介绍。
# 特色分析
##  主题新颖
本作品主要结合了当前人们比较关注的点——“绿色与环保”来进行设计功能。结合当下流行的“互联网+”的潮流。独具匠心的设计了实用的小功能，。通过我们的app，推行“人人都是观察员，人人都是环保员“的全新理念。	
## 功能新颖
本作品结合当下实际情况，设计出符合目前我们比较需要的却没有实现的功能。我们的功能设计从实际需求出发，也基于我们的现实技术水平来实现功能，下面分别介绍我们的功能。
举报环保信息
源于当我们看到有环境问题却不知道怎么去反映时的灵感。于是我们设计了举报环境问题的功能，用于解决用户发现问题的时候投诉无门的尴尬。
垃圾回收
源于我们把自己平时喝完的矿泉水瓶子都收集起来，最后没地方放的时候发现我们不知道谁能收这些东西。于是我们就设计了这个一个垃圾回收的功能，通过此功能，我们可以很方便的找到我们附近能收这种垃圾的人，以及迅速的联系到他。
旧物利用
这个功能就是源于“图书漂流”的思想，我们想，一本书可以漂流下去，一个一个的传下下去，那么其他物品中应该也有可以的。于是我们就大胆设计了旧物利用这一功能，用于实现对旧物的重复利用。
##  界面新颖
主界面采用了当下比较流的可滑动式布局，能给用户一种特别的感觉。
# 难点和解决方案
## 搜索附近的垃圾回收者
首先每一个认证成为垃圾回收者的用户在认证信息的收时候，我们会默认收集该用户的位置信息（经、纬度），存到后端云数据库。当其他用户想查看附近的垃圾回收者的时候，首先通过GPS定位，将自己的位置信息上传，到bmob后端云数据库，然后通过分析经纬度的值，实现查询多少m范围内附近的人（因为目前注册的人比较少，为了保障能搜索到数据，我们默认搜索的范围是全球）。然后通过经纬度换算，实现计算用户与垃圾回收者之间的距离。
 ## 环保圈信息的发布和接受其他用户评论和喜欢
在环保圈这一块，如何发布环保圈信息也是一个难点之一。首先我们需要明确发布环保圈的信息里面可以有些什么，针对目前该项目的需求，我们需要发布的信息包括，标题、内容、图片（可省略）。但是我们的后端数据库需要建立的表的内容应该不仅仅只有这些。在这个项目上，我们在后端数据库上建立了多个表，并与用户表建立联系，具体参见ER图：
 
![ dynamic的ER图.png](http://upload-images.jianshu.io/upload_images/2178834-556ac0e87ee5583b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

从上面的ER图可以看出，在dynamic表中likes和author属性与user表联系，这样我们就可以实现发布的动态与自己的身份信息绑定，并且告知查看动态的用户该动态的作者。在评论表的设计上，我们采用与dynamic表类似的设计，ER图如下：
 
![Comment的ER图.png](http://upload-images.jianshu.io/upload_images/2178834-bef3483ff59a502a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 资源文件的缓存问题
因为我们的app实现功能是需要联网的，对网络要求比较高。当没有网络、或者网络信号比较差的时候，如果不做特殊的处理，用户体验就会比较差，因此我们实现了缓存的机制，当网络比较好的时候，我们可以预先缓存一定的数据到手机数据库中，下次访问的时候可以直接访问手机数据库，进而提高数据的加载速度，也解决了没有网、信号差的时候不能加载数据的问题。
