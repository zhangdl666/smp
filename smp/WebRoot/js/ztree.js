var nodeCount=0;
var checkCount=0;
var chkboxType;

		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		} 
		function filter1(node) {
			return !node.isParent && node.isFirstNode;
		}
		function beforeClick(treeId, treeNode) {
/* 			if (!treeNode.isParent) {
				alert("请选择父节点");
				return false;
			} else {
				return true;
			} */
		}
		function beforeAsync(treeId, treeNode) {
			curAsyncCount++;
			return true;
		}
		function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
			curAsyncCount--;

			if (curAsyncCount <= 0) {
				curStatus = "";
				if (treeNode!=null) asyncForAll = true;
			}
		}
		function onAsyncSuccess(event, treeId, treeNode, msg) {
			curAsyncCount--;
			if (curStatus == "expand") {
				expandNodes(treeNode.children);
			} else if (curStatus == "async") {
				asyncNodes(treeNode.children);
			}
			if (curAsyncCount <= 0) {
				if (curStatus != "init" && curStatus != "") {
					pupclose();
					asyncForAll = true;
					var zTree = $.fn.zTree.getZTreeObj(treeName);
		            if(ajaxStatus=="search"){//查询
		            zTree.cancelSelectedNode();//取消选中
				     var regexp = /^[a-zA-Z]+$/;
		             var keys=$.trim(key.get(0).value);
		            if(regexp.test(keys)){
		            	   keys=keys.toLocaleLowerCase();
		            	   nodeList = zTree.getNodesByParamFuzzy("pinyin",keys);
		             }else{
	    		           nodeList = zTree.getNodesByParamFuzzy("name",keys);
		            }
	    		     updateNodes(true);
		            }else if(ajaxStatus=="huixian"){//默认回显展开
		            	window.clearInterval(t1); 
		            	var checkedIdstr=checkedIds.split(",");
		                for(var i=0;i<checkedIdstr.length;i++){
		                	var node = zTree.getNodeByParam("id", checkedIdstr[i]);
								nodeList = [node];
		                }
		                updateNodes(true);
		            }
	    		
				}
				curStatus = "";
			}
			
			if(nodeCount==0&&checkedIds!=null&&checkedIds==""&&checkedIds!="undefined"){
				var zTree = $.fn.zTree.getZTreeObj(treeName);
				zTree.expandNode(zTree.getNodes()[0], true, false, false);
//	            zTree.expandNode(zTree.getNodes()[0][3], true, false, false);
//				zTree.expandNode(zTree.getNodes()[0].children[2], true, false, false);
//				var nodes = zTree.getNodes()[0].children;
		        nodeCount++;
				pupclose();
			}
//			if(q=1){
//				var zTree = $.fn.zTree.getZTreeObj(treeName);
//				pupclose();
//				q=0;
//			}
			
			if(checkCount==0){
				if(chkboxType=="no"){
					var zTree = $.fn.zTree.getZTreeObj(treeName);
					var type = { "Y":"", "N":""};
					zTree.setting.check.chkboxType = type;
				}
				checkCount++;
			}
		}
		
		function selectAjax (cids){
			if(cids==""||cids=="undefined"){
				window.clearInterval(t1); 
			}else{
				ajaxStatus="huixian";
				pupopen();
				asyncAll();
			}
		}
	   

		function refreshNode(e) {
			var zTree = $.fn.zTree.getZTreeObj(treeName),
			type = e.data.type,
			silent = e.data.silent,
			nodes = zTree.getSelectedNodes();
			if (nodes.length == 0) {
				alert("请先选择一个父节点");
			}
			for (var i=0, l=nodes.length; i<l; i++) {
				zTree.reAsyncChildNodes(nodes[i], type, silent);
				if (!silent) zTree.selectNode(nodes[i]);
			}
		}
		
		/************************************************** 异步展开*******************************************************/
		var curStatus = "init", curAsyncCount = 0, asyncForAll = false,
		goAsync = false;
		function expandAll() {
			if (!check()) {
				return;
			}
			var zTree = $.fn.zTree.getZTreeObj(treeName);
			if (asyncForAll) {
				zTree.expandAll(true);
			} else {
				expandNodes(zTree.getNodes());
				if (!goAsync) {
					curStatus = "";
					
				}
			}
		}
		function expandNodes(nodes) {
			if (!nodes) return;
			curStatus = "expand";
			var zTree = $.fn.zTree.getZTreeObj(treeName);
			for (var i=0, l=nodes.length; i<l; i++) {
				zTree.expandNode(nodes[i], true, false, false);
				if (nodes[i].isParent && nodes[i].zAsync) {
					expandNodes(nodes[i].children);
				} else {
					goAsync = true;
				}
			}
		}

		function asyncAll() {
			if (!check()) {
				return;
			}
			var zTree = $.fn.zTree.getZTreeObj(treeName);
			if (asyncForAll) {
			} else {
				asyncNodes(zTree.getNodes());
				if (!goAsync) {
					curStatus = "";
					pupclose();
					serchAll(zTree);
				}
			}
		}
		function asyncNodes(nodes) {
			if (!nodes) {pupclose(); return;}
			curStatus = "async";
			var zTree = $.fn.zTree.getZTreeObj(treeName);
			for (var i=0, l=nodes.length; i<l; i++) {
				if (nodes[i].isParent && nodes[i].zAsync) {
					asyncNodes(nodes[i].children);
				} else {
					goAsync = true;
					zTree.reAsyncChildNodes(nodes[i], "refresh", true);
				}
			}
			if(ajaxStatus=="search"){
				if (asyncForAll) {
			    pupclose();
			    serchAll(zTree);
				}
			}
		}
		function serchAll(zTree){
		    if(ajaxStatus=="search"){//查询
	            zTree.cancelSelectedNode();//取消选中
	            var regexp = /^[a-zA-Z]+$/;
	            var keys=$.trim(key.get(0).value);
	            if(regexp.test(keys)){
	            	   keys=keys.toLocaleLowerCase();
	            	   nodeList = zTree.getNodesByParamFuzzy("pinyin",keys);
	             }else{
    		           nodeList = zTree.getNodesByParamFuzzy("name",keys);
	            }
    		    updateNodes(true);
            }else if(ajaxStatus=="huixian"){//默认回显展开
            	window.clearInterval(t1); 
            	var checkedIdstr=checkedIds.split(",");
                for(var i=0;i<checkedIdstr.length;i++){
                	var node = zTree.getNodeByParam("id", checkedIdstr[i]);
						nodeList = [node];
                }
                updateNodes(true);
	       }
		}
		function reset() {
			if (!check()) {
				return;
			}
			asyncForAll = false;
			goAsync = false;
			$.fn.zTree.init($("#tree"), setting);
		}

		function check() {
			if (curAsyncCount > 0) {
				return false;
			}
			return true;
		}
		/************************************************** 搜索*******************************************************/
		function focusKey(e) {
			if (key.hasClass("empty")) {
				key.removeClass("empty");
			}
		}
		function blurKey(e) {
			if (key.get(0).value === "") {
				key.addClass("empty");
			}
		}

		var lastValue = "", nodeList = [], fontCss = {}; 
		var ajaxStatus="";
		function searchNode() {
			ajaxStatus="search";
			var zTree = $.fn.zTree.getZTreeObj(treeName);
			if($.trim(key.get(0).value)!=""){
				var isloadwc=true;
				var len=zTree.getNodes().length;
				for(var j=0; j<len; j++){
					if(zTree.getNodes()[j]!=null){
						if(zTree.getNodes()[j].children!=null){
							for (var i=0, l=zTree.getNodes()[j].children.length; i<l; i++) {
			                       if(zTree.getNodes()[j].children[i]!=null&&zTree.getNodes()[j].children[i].isParent==true&&zTree.getNodes()[j].children[i].children==null){//存在子节点
			                    	   isloadwc=false; 
			                    	   break;
			                       }  
						    }
						if(isloadwc){
						  asyncForAll=true;
						}else if(isloadwc==false){
		                 asyncForAll=false;
						 break;
						}
					}else{
						if(zTree.getNodes()[j].isParent==true){
							 isloadwc=false; 
							 asyncForAll=false;
							 break;
						}
					}
				}
			}
			if (asyncForAll==false) {
				pupopen();
				asyncAll();
			}else{
				
				zTree.cancelSelectedNode();//取消选中
				     var regexp = /^[a-zA-Z]+$/;
		             var keys=$.trim(key.get(0).value);
		            if(regexp.test(keys)){
		            	   keys=keys.toLocaleLowerCase();
		            	   nodeList = zTree.getNodesByParamFuzzy("pinyin",keys);
		             }else{
	    		           nodeList = zTree.getNodesByParamFuzzy("name",keys);
		            }
    		        updateNodes(true);
			}
		 }else{
			 alert("没有要查询的信息!");
		 }
		}
		function updateNodes(highlight) {
			var zTree = $.fn.zTree.getZTreeObj(treeName);
			if(nodeList.length==0){
				alert("未查询到相关信息!");
			}
			for( var i=0, l=nodeList.length; i<l; i++) {
				  zTree.selectNode(nodeList[i],true);
			}
		}
	
		
		function collapseAll() {
			var zTree = $.fn.zTree.getZTreeObj(treeName);
				zTree.expandAll(false);
		}
		function pupopen(){
		    document.getElementById("ztreebg").style.display="block";
			document.getElementById("ztreebg").style.height = Math.max(document.documentElement.clientHeight,document.documentElement.scrollHeight) + "px";
		    document.getElementById("ztreepopbox").style.display="block" ;
		}

		function pupclose(){
		  	document.getElementById("ztreebg").style.display="none";
		    document.getElementById("ztreepopbox").style.display="none" ;
		}
		var key;
		var t1;