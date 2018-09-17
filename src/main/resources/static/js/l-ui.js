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
    function dataTable(cof) {
        var statrtTime = new Date().getTime();
        var table=document.getElementById(cof.id);
        if(table == undefined||table == null) {
            return false;
        }

        renderTable.th(renderTable.wrap(table),cof.column);
        var column=cof.column;
        //this.a(th,tb,cof.column);
        console.log(cof.id+"数据表格树初始化："+(new Date().getTime()-statrtTime)+"ms");
    };
    var tp=dataTable.prototype;
    tp.a=function(){
        var t=document.createElement("table");
    };
    var renderTable={
        "wrap":function (table) {
            var tp=table.parentNode,tc=table.cloneNode(),tw=document.createElement("div"),
                th=document.createElement("div"),tb=document.createElement("div"),tf=document.createElement("div");
            tw.className="dataTable-wrap",th.className="dataTable-thead",
                tb.className="dataTable-tbody",tf.className="dataTable-tfoot";
            table.remove(),tb.appendChild(tc),tw.appendChild(th),tw.appendChild(tb),tw.appendChild(tf),tp.appendChild(tw);
            return [this.thead(th),this.tbody(tc)];
        },
        "thead":function (th) {
            var table = document.createElement("table"),
                thead=document.createElement("thead"),tr = document.createElement("tr");
            return thead.appendChild(tr),table.appendChild(thead),th.appendChild(table),tr;
        },
        "tbody":function (tb) {
            var thead = document.createElement("thead"),tr = document.createElement("tr"),
                tbody=document.createElement("tbody");
            return thead.appendChild(tr),tb.appendChild(thead),tb.appendChild(tbody),tr;
        },
        "th":function (w,column) {
            for(var i=0;i<column.length;i++) {
                var th=document.createElement("th"),th2=th.cloneNode(),div=document.createElement("div");
                th.innerHTML=div.innerHTML=column[i].title;
                th2.appendChild(div);
                w[0].appendChild(th),w[1].appendChild(th2);
            }
        },
    };
    window.initDataTablePlugIn=function (cof) {
        return new dataTable(cof);
    };
})(window);