/**
 * 
 */


$(function(){
	//从URL里获取shopId参数的值
	var shopId = getQueryString('shopId');
	//由于店铺注册和编辑使用的是同一个页面，
	//该标识用来标明本次是添加还是编辑操作
	var isEdit = shopId ? true : false;
	//用于店铺 注册时候的店铺类别以及区域列表的初始化URL
	var initUrl = '/o2o/shopadmin/getshopinitinfo';
	//注册店铺的URL
	var registerShopUrl = '/o2o/shopadmin/registershop';
	//编辑店铺前需要获取店铺信息，这里为获取当前店铺信息的URL
	var shopInfoUrl = '/o2o/shopadmin/getshopbyid?shopId=' + shopId;
	//编辑店铺URL
	var editshopUrl = '/o2o/shopadmin/modifyshop';
	//判断是编辑操作还是注册操作
	if(!isEdit){
		getShopInitInfo();
	}else{
		getShopInfo(shopId);
	}
	
	//通过店铺Id获取店铺信息
	function getShopInfo(shopId){
		$.getJSON(shopInfoUrl, function(data){
			if(data.success){
				//若访问成功，则依据后台传递过来的店铺信息为表单元素赋值
				var shop = data.shop;
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				//给店铺列别选定原先的店铺列别值
				var shopCategory = '<option data-id="'
					+ shop.shopCategory.shopCategoryId +'"selected>'
					+ shop.shopCategory.shopCategoryName + '</option>';
				var tempAreaHtml = '';
				//初始化区域列表
				data.areaList.map(function(item, index){
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
					+ item.areaName + '</option>';
				});
				$('#shop-category').html(shopCategory);
				$('#shop-category').attr('disabled', 'disabled');
				$('#area').html(tempAreaHtml);
				$("#area option[data-id='"+ shop.area.areaId +"']").attr('selected', "selected");
				
			}
		});
	}
	//从后台获取店铺分类以及区域等信息填充到前台现实
	//验证表单的输入，待完成
	//取得所有店铺类别以及区域信息，并分别赋值进类别列表以及区域列表
	function getShopInitInfo(){
		$.getJSON(initUrl, function(data){
			if(data.success){
				var tempHtml = '';
				var tempAreaHtml = '';
				data.shopCategoryList.map(function(item,index){
					tempHtml += '<option data-id="'+ item.shopCategoryId
					+ '">' + item.shopCategoryName + '</option>';
				});
				data.areaList.map(function(item, index){
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
					+ item.areaName + '</option>'
				});
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml);
			}
		});
	}
	//提交按钮的时间响应，分别对店铺注册和编辑操作做不同响应
	//将表单的信息获取到将他转发到后台
	$('#submit').click(function(){
		//创建shop对象
		var shop = {};
		if(isEdit){
			//若属性编辑则给shopId赋值
			shop.shopId = shopId;
		}
		//获取表单里面的数据并填充进对应的店铺属性中
		shop.shopName = $('#shop-name').val();
		shop.shopAddr = $('#shop-addr').val();
		shop.phone = $('#shop-phone').val();
		shop.shopDesc = $('#shop-desc').val();
		//选择选定好的店铺类别
		shop.shopCategory = {
				shopCategoryId: $('#shop-category').find('option').not(function(){
					return !this.selected;
				}).data('id')
		};
		//选择选定好的区域信息
		shop.area = {
				areaId: $('#area').find('option').not(function(){
					return !this.selected;
				}).data('id')
		};
		//获取上传的图片文件流
		var shopImg = $('#shop-img')[0].files[0];
		//生成表单对象，用于接收参数并传递参数给后台
		var formData = new FormData();
		//添加图片流进表单对象里
		formData.append('shopImg',shopImg);
		//将shop json对象转成字符流保存至表单对象key为shopStr的键值对里
		formData.append('shopStr',JSON.stringify(shop));
		//获取表单里输入的验证码
		var verifyCodeActual = $('#j_captcha').val();
		if(!verifyCodeActual){
			$.toast('请输入验证码');
			return;
		}
		formData.append('verifyCodeActual', verifyCodeActual);
		//将数据提交至后台处理相关操作
		$.ajax({
		url: (isEdit ? editshopUrl : registerShopUrl),
		type: 'POST',
		data: formData,
		contentType: false,
		processData: false,
		cache: false,
		success: function(data){
			console.log(data);
			if(data.success){
				$.toast('提交成功！');
				//若为注册操作，成功后返回店铺列表页
				if(!isEdit){
					window.location.href = "/o2o/shopadmin/shoplist";
				}
			}else{
				$.toast('提交失败！' + data.errMsg);
			}
			//点击验证码图片的时候，注册码会改变
			$('#captcha_img').click();
		}
		});
	});	
});

