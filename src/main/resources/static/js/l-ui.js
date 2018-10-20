;"use strict";
/* 公用方法 */
;(function (window) {
    var DomOperate={
      c:function (t) {
          return document.createElement(t);
      }
    };
    window.LDom=DomOperate;
})(window)

/* 结构树插件 */
;(function (window) {
    function initTree(cof) {
        var statrtTime = new Date().getTime();
        this.ulNode = document.getElementById(cof.id);
        this.data=cof.data;
        this.renderNode();
        bindNodeEvent.nodeExpand(this.ulNode);
        console.log(cof.id+"结构树初始化："+(new Date().getTime()-statrtTime)+"ms");
        return "";
    };
    var pt=initTree.prototype;
    pt.renderLiNode=function(d){
        var li=LDom.c("li"),i0=LDom.c("i"),a=LDom.c("a"),i1=LDom.c("i"),
            i2=LDom.c("i"),span=LDom.c("span"),ul=LDom.c("ul");
        i0.className = "ztree-icon ztree-icon-default ztree-icon-none";
        i1.className = "ztree-icon ztree-icon-default ztree-icon-checkbox";
        i2.className = "ztree-icon fa fa-folder";
        span.innerHTML = d.name;
    };
    pt.renderNode=function(c,e){
        var _ = this;
        var data = c == undefined?_.data:c,
            w = e == undefined?_.ulNode:e;
        for (var i=0;i<data.length;i++){
            var li = document.createElement("li"),
                i0 = document.createElement("i"),
                a  = document.createElement("a"),
                i1 = document.createElement("i"),
                i2 = document.createElement("i"),
                span = document.createElement("span"),
                ul = document.createElement("ul");

            i1.className = "ztree-icon  ztree-icon-default ztree-icon-checkbox";
            //i2.className = "ztree-icon fa fa-folder";
            span.innerHTML = data[i].name;
            if(i == data.length-1){
                li.className = "li-last";
            }
            if(data[i].children == undefined || data[i].children == null ||data[i].children.length > 0) {
                i0.className = "ztree-icon ztree-icon-default ztree-icon-close";
                i2.className = "ztree-icon fa fa-folder-open lui-color-warning";
                _.renderNode(data[i].children,ul);
            }else {
                i0.className = "ztree-icon ztree-icon-default ztree-icon-none";
                i2.className = "ztree-icon fa fa-folder lui-color-warning";
            }
            a.appendChild(i1),a.appendChild(i2),a.appendChild(span),li.appendChild(i0),li.appendChild(a),li.appendChild(ul);
            w.appendChild(li);
        }
    };

    var bindNodeEvent={
        "nodeExpand":function (ul) {
            ul.addEventListener("click",function (ev) {
                if(ev.target.className.indexOf("ztree-icon-open")>-1){
                    ev.target.classList.remove("ztree-icon-open"),ev.target.classList.add("ztree-icon-close");
                    var children = ev.target.parentNode.childNodes;
                    for (var i=0;i<children.length;i++){
                        if(children[i].nodeName == "UL"){
                            children[i].style.display="block";
                            break;
                        }
                    }
                }else if(ev.target.className.indexOf("ztree-icon-close")>-1){
                    ev.target.classList.remove("ztree-icon-close"),ev.target.classList.add("ztree-icon-open");
                    var children = ev.target.parentNode.childNodes;
                    for (var i=0;i<children.length;i++){
                        if(children[i].nodeName == "UL"){
                            children[i].style.display="none";
                            break;
                        }
                    }
                }
            });
        },
    };

    if(window.initTreePlugIn == undefined) {
        window.initTreePlugIn=function (cof) {
            return new initTree(cof);
        };
    }else {
        console.error("结构树插件加载失败！window.initTreePlugIn 方法重复");
    }
    //var ztreeList = document.getElementsByClassName("");
})(window);

/* 数据表格插件 */
;(function (window) {
    var Ajax={
        "send":function (cof) {
            var defaultOption={
                "url":"",
                "headers":{},
                "type":"",
                "data":"",
                "contentType":"",
                "_beforeSend":null,
                "_sFn":null,
                "_eFn":null,
                "_cFn":null,
            };
            defaultOption = $.extend(defaultOption,cof);
            $.ajax({
                headers:defaultOption.headers,
                url: defaultOption.url,
                type: defaultOption.type,
                data:defaultOption.data,
                beforeSend:function(xhr){
                    if(typeof defaultOption._beforeSend == "function"){
                        defaultOption._beforeSend(xhr);
                    }
                },
                success: function (s) {
                    if(typeof defaultOption._cFn == "function"){
                        defaultOption._sFn(s);
                    }
                },
                error:function (e) {
                    if(typeof defaultOption._eFn == "function"){
                        defaultOption._eFn(e);
                    }
                },
                complete:function (c) {
                    if(typeof defaultOption._cFn == "function"){
                        defaultOption._cFn(c);
                    }
                }
            });
        },
        "GET":function(url,beforeSend,_sFn,_eFn,_cFn){
            this.send({
                "url":url,
                "type":"GET",
                "_beforeSend":beforeSend,
                "_sFn":_sFn,
                "_eFn":_eFn,
                "_cFn":_cFn,
            });
        },
        "POST":function(url,para,beforeSend,_sFn,_eFn,_cFn){
            this.send({
                "url":url,
                "type":"POST",
                "data":para,
                "_beforeSend":beforeSend,
                "_sFn":_sFn,
                "_eFn":_eFn,
                "_cFn":_cFn,
            });
        },

    };
    function dataTable(cof) {
        var tE=$("#"+cof.id);
        if(tE.length == 0) {
            console.error("找不到元素："+cof.id);
            return false;
        };
        if(tE.length > 1){
            console.error("元素的Id："+cof.id+"重复");
            return false;
        }
        if(!cof.url){
            console.error("请指定数据列表接口");
            return false;
        };
        if(!cof.column){
            console.error("请补充数据列");
            return false;
        };

        var defaultOption={
            "id":null,
            "height":null,
            "column":null,
            "fixedLeft":-1,
            "fixedRight":-1,
            "pagination": true, //是否显示分页
            "pagePlug":false,
            "pageSizeList": [10, 25, 50, 100],  //可供选择的每页的行数（*）
            "sidePagination": "client",//分页方式：client客户端分页，server服务端分页
            "sortable": true,       //是否启用排序
            "url":null,
            "method":"POST",
            "queryParams":null,
            "setResponsePageInfo":null,
            "requestSuccessCallBack":null,
            "requestErrorCallBack":null,
            "requestCompleteCallBack":null,
            "initComplete":null,
        };

        var statrtTime = new Date().getTime();
        $.extend(defaultOption,cof);
        cof=null;
        var tableInfo={
            "id":"#"+defaultOption.id,
            "node":null,
        };
        var pageInfo={
            "number":1,
            "pageOffset":0,
            "pageSize":10,
            "currentPage":0,
            "pages":0,
            "data":[],
            "recordsTotal":0,
        };
        tableInfo.node=renderTable.wrap(defaultOption.id,tableInfo.id,defaultOption.height),
            renderTable.th(tableInfo.node,defaultOption.column);

        var prApi={};
        prApi.request=function () {
            if(typeof defaultOption.queryParams == "function") {
                var para = {};
                if(typeof  defaultOption.queryParams == "function"){
                    para = defaultOption.queryParams();
                }
                para = $.extend(para,{"pageOffset":pageInfo.pageOffset,"pageSize":pageInfo.pageSize});
                Ajax[defaultOption.method](defaultOption.url,para,null,function (result) {
                    if(typeof  defaultOption.requestSuccessCallBack == "function"){
                        defaultOption.requestSuccessCallBack(result);
                    }
                    if(typeof  defaultOption.setResponsePageInfo == "function"){
                        var pI = defaultOption.setResponsePageInfo(result);
                        puApi.renderData(pI.list);
                    }
                    //puApi.renderData(result);
                },function (e) {
                    if(typeof  defaultOption.requestErrorCallBack == "function"){
                        defaultOption.requestErrorCallBack(e);
                    }
                },function (c) {
                    if(typeof  defaultOption.requestCompleteCallBack == "function"){
                        defaultOption.requestCompleteCallBack(c);
                    }
                });
            }
        };
        var puApi={};
        puApi.reload=function () {
            pageInfo.start=0;
            prApi.request();
        };
        puApi.renderData=function (data) {
            console.log(data);
            var e = tableInfo.node.find(tableInfo.id).find("tbody").html("");
            var body = "";
            if(data.length > 0){
                for (var i=0;i<data.length;i++){
                    var tr="<tr>";
                    for (var j=0;j<defaultOption.column.length;j++){
                        tr=tr+"<td>"+data[i][defaultOption.column[j].data]+"</td>";
                    }
                    tr=tr+"</tr>"
                    body=body+tr;
                }
                e.html(body);
            }else {
                e.html("<td colspan='"+defaultOption.column.length+"'>没有数据</td>");
            }
            dataTable_Api.draw(tableInfo.node);
        };

       /* window.addEventListener("resize",function (ev) {
            dataTable_Api.draw(tableInfo.node);
        });*/
        /*tableInfo.node.parentNode.addEventListener("scroll",function (evt) {
            tableInfo.thead.style.marginLeft = -this.scrollLeft+"px";
        });*/
        puApi.reload();
        this.Api=function () {
            return puApi;
        };
        window[defaultOption.id+"Object"]=this;
        console.log(defaultOption.id+"数据表格树初始化："+(new Date().getTime()-statrtTime)+"ms");

    };
    var tp=dataTable.prototype;
    tp.bindEvent=function(id){

    };
    tp.Api=function () {
        return this.Api;
    };
    var renderTable={
        "wrap":function (i,ic,h) {
            var w = $(ic).parent().html(
                "<div  id='"+i+"_wrap' class='dataTable-wrap'>" +
                "<div class='dataTable-thead'><table><thead><tr></tr></thead></table></div>"+
                "<div class='dataTable-tbody'><table id='"+i+"'><thead><tr></tr></thead><tbody></tbody></table></div>"+
                "<div class='dataTable-tpage'></div>"+
                "</div>");
            if(h) {w.find("dataTable-tbody").css("height",h);}
            return w.find("#"+i+"_wrap");
        },
        "th":function (n,e) {
            var a = n.find(".dataTable-thead thead tr"),b=n.find(".dataTable-tbody thead tr"),c="",d="";
            for(var i=0;i<e.length;i++) {
                c=c+"<th>"+e[i].title+"</th>";
                d=d+"<th><div>"+e[i].title+"</div></th>";
            }
            a.html(c),b.html(d);
        },
        "page":function(){

        }
    };
    var dataTable_Api={
        "draw":function (n) {
            if(n){
                var t1 = n.find(".dataTable-thead thead tr th"),
                    t2 = n.find(".dataTable-tbody tbody tr").eq(0).find("td"),t=0;
                if(t2.length == 0){
                    n.find(".dataTable-thead table").css("width","100%");
                    t1.css({
                        "width":"auto",
                        "min-width":"",
                        "max-width":"",
                    });
                }
                n.find(".dataTable-thead table").css("width",n.find(".dataTable-tbody table").outerWidth()+"px");
                for(var i=0;i<t2.length;i++){
                    t=window.getComputedStyle(t2[i],null).width;
                    t1.eq(i).css({
                        "min-width":t,
                        "max-width":t,
                    });
                }


            }else {

            }
        },
        "api":function (id) {
            return window[id+"Object"];
        },
    };
    window.initDataTablePlugIn=function (cof) {
        new dataTable(cof);
    };
    window.dataTable_Api=dataTable_Api;
})(window);

/* 分页插件 */
;(function (window) {
    var  pagePlug=function (cof) {
        var node=document.getElementById(cof.id),html="";
        var showPage = cof.showPage,index = Math.floor(cof.showPage/2);
        var pageArray=[];

        if(cof.pages == 0) {
            html="<li class='l-ui-page-disabled'>上一页</li><li class='l-ui-page-disabled'>下一页</li>";
        }else {
            if(cof.currentPage>cof.pages-1){
                cof.currentPage=cof.pages-1;
            }
            for (var i=0;i<showPage;i++){
                pageArray.push(-1);
            }
            if(cof.currentPage !=0){
                html="<li class='l-ui-page-item' data-page='"+(cof.currentPage-1)+"'>上一页</li>";
            }else {
                html="<li class='l-ui-page-disabled'>上一页</li>";
            }
            if(cof.currentPage-index<0){
                for (var i=0;i<showPage;i++){
                    pageArray[i]=i;
                }
            }else if(cof.currentPage+index>=cof.pages){
                var t = cof.pages-1;
                for (var i=showPage-1;i>=0;i--){
                    pageArray[i]=t--;
                }
            }else {
                for (var i=0;i<showPage;i++){
                    pageArray[i]=cof.currentPage-index+i;
                }
            }
            console.log(pageArray);
            if(pageArray[0]-1>=0){
                html+="<li data-page='0'>1</li>";
                if(pageArray[0]-1>0) {
                    html+= "<li>...</li>";
                }
            }
            for (var i=0;i<pageArray.length;i++){
                if(pageArray[i]>=0 && pageArray[i]<cof.pages){
                    html+="<li data-page='"+pageArray[i]+"'>"+(pageArray[i]+1)+"</li>";
                }
            }
            if(pageArray[showPage-1]<=cof.pages-1 && pageArray[showPage-1]!=cof.pages-1){
                if(pageArray[showPage-1]+1<cof.pages-1){
                    html+="<li>...</li>";
                }
                html+="<li data-page='"+(cof.pages-1)+"'>"+cof.pages+"</li>";
            }
            if((cof.currentPage+1) == cof.pages){
                html+="<li class='l-ui-page-disabled'>下一页</li>";
            }else {
                html+="<li class='l-ui-page-item' data-page='"+(cof.currentPage+1)+"'>下一页</li>";
            }
        }


        node.innerHTML=html;
        var li = node.children;
        for(var i=0;i<li.length;i++){
            console.log();
            if(li[i].dataset["page"]!=undefined){
                if(cof.currentPage == li[i].dataset["page"]){
                    li[i].className="l-ui-page-active";
                }else {
                    li[i].className="l-ui-page-item";
                }
                li[i].addEventListener("click",function (evt) {
                    cof.callback(parseInt(this.dataset["page"]),evt);
                });
            }
        }
    };
    window.initPagePlug=pagePlug;
})(window);