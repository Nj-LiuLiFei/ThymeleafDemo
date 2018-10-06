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
        "GET":function (url,para,type,_sFn,_eFn,_cFn) {
            if(para!=undefined&&para!=null&&para!="") {
                url = url + "?" + para;
            }
            $.ajax({
                url: url,
                type: "GET",
                contentType: "application/json",
                success: function (data) {
                    _sFn(data);
                },
                error:function (e) {
                    _eFn(e);
                },
                complete:function (c) {
                  _cFn(c);
                }
            });
        },
        "POST":function (url,para,type,_sFn,_eFn,_cFn) {
            $.ajax({
                url: url,
                type: "POST",
                data:para,
                contentType: "application/json",
                success: function (data) {
                    _sFn(data);
                },
                error:function (e) {
                    _eFn(e);
                },
                complete:function (c) {
                    _cFn(c);
                }
            });
        },
    };
    function dataTable(cof) {
        var tableElement=document.getElementById(cof.id);
        if(tableElement == undefined||tableElement == null) {
            console.error("找不到元素："+cof.id);
            return false;
        };
        if(!cof.url){
            console.error("请指定数据列表接口");
            return false;
        };
        if(!cof.column){
            console.error("请补充数据列");
            return false;
        };
        var statrtTime = new Date().getTime();
        var tableInfo={
            "id":cof.id,
            "formId":cof.formId,
            "node":"",
            "thead":"",
            "tbody":"",
            "tfoot":"",
            "column":cof.column,
            "showColumn":0,
            "data":"",
            "url":cof.url,
            "method":"GET",
            "page":cof.page,
        };
        var tableNode = {

        }
        var pageInfo={
            "number":1,
            "start":0,
            "displayTotal":10,
            "currentPage":0,
            "pages":0,
            "recordsTotal":0,
        };
        var prApi={};
        prApi.request=function () {
            var para = $("#"+tableInfo.formId).serialize();
            para+="&start="+pageInfo.start+"&displayTotal="+pageInfo.displayTotal;
            Ajax[tableInfo.method](tableInfo.url,para,null,function (result) {
                puApi.renderData(result);
            },function (e) {

            },function (c) {

            });
        };
        var puApi={};
        puApi.reload=function () {
            pageInfo.start=0;
            prApi.request();
        };
        puApi.renderData=function (data) {
            console.log(data);
            tableInfo.tbody.innerHTML="";
            var body = document.createDocumentFragment();
            if(data.length > 0){
                for (var i=0;i<data.length;i++){
                    var tr=document.createElement("tr");
                    for (var j=0;j<tableInfo.column.length;j++){
                        var td=document.createElement("td");
                        td.innerHTML=data[i][tableInfo.column[j].data];
                        tr.appendChild(td);
                    }
                    body.appendChild(tr);
                }
                tableInfo.tbody.appendChild(body);
            }else {
                tableInfo.tbody.innerHTML="<td colspan='"+tableInfo.showColumn+"'>没有结果列表</td>";
            }


            dataTable_Api.draw(tableInfo.id);
        };
        renderTable.wrap(tableElement,tableInfo.id,cof.height),
            tableInfo.thead=document.getElementById(cof.id+"_dataTable_thead_table"),
            tableInfo.node=document.getElementById(cof.id),
            tableInfo.tbody=tableInfo.node.children[1];
            tableInfo.showColumn=renderTable.th(tableInfo.thead,tableInfo.node,tableInfo.column);
        window.addEventListener("resize",function (ev) {
            dataTable_Api.draw(tableInfo.id);
        });
        tableInfo.node.parentNode.addEventListener("scroll",function (evt) {
            tableInfo.thead.style.marginLeft = -this.scrollLeft+"px";
        });
        console.log(cof.id+"数据表格树初始化："+(new Date().getTime()-statrtTime)+"ms");
        cof=null;
        puApi.reload();
        this.Api=function () {
            return puApi;
        };
        window[tableInfo.id+"Object"]=this;
    };
    var tp=dataTable.prototype;
    tp.bindEvent=function(id){

    };
    tp.Api=function () {
        return this.Api;
    };
    var renderTable={
        "wrap":function (table,id,h) {
            table.innerHTML="<thead><tr></tr></thead><tbody></tbody>",
            table.parentNode.innerHTML="<div class='dataTable-wrap'>" +
                "<div class='dataTable-thead'>" +
                    "<table id="+id+"_dataTable_thead_table><thead><tr></tr></thead></table></div>" +
                "<div class='dataTable-tbody' style='max-height: "+h+"'>"+table.outerHTML+"</div>" +
                "<div class='dataTable-tfoot'></div>" +
                "<div class='dataTable-tpage'></div>" +
                "</div>";
        },
        "th":function (a,b,c) {
            var th=a.children[0].children[0],tb=b.children[0].children[0],t=0;
            for(var i=0;i<c.length;i++) {
                var h=document.createElement("th"),h2=h.cloneNode(),div=document.createElement("div");
                h.innerHTML=div.innerHTML=c[i].title;
                h2.appendChild(div);
                th.appendChild(h),tb.appendChild(h2);
                t++;
            }
            return t;
        },
    };
    var dataTable_Api={
        "draw":function (id) {
            if(id){
                var table1=document.getElementById(id+"_dataTable_thead_table"),
                    table2=document.getElementById(id),
                    th=table1.children[0].children[0],
                    th2=table2.children[0].children[0];
                for(var i=0;i<th.childElementCount;i++){
                    th.children[i].style.minWidth = th.children[i].style.maxWidth =window.getComputedStyle(th2.children[i],null).width;
                }
                table1.style.width = table2.offsetWidth+"px";
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