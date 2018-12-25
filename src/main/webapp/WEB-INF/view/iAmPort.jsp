<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
</body>
<!-- 아임포트 자바스크립트는 jQuery 기반으로 개발되었습니다 -->
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js" ></script>

<script type="text/javascript">
    var IMP = window.IMP; // 생략가능
    IMP.init('imp10002917'); // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용

    /* 중략 */

    //onclick, onload 등 원하는 이벤트에 호출합니다
    IMP.request_pay({
        pg : 'inicis', // version 1.1.0부터 지원.
        pay_method : 'card',
        merchant_uid : 'merchant_' + new Date().getTime(),
        name : '주문명:결제테스트',
        amount : ${payInfo.price},
        buyer_email : 'iamport@siot.do',
        buyer_name : '구매자이름',
        buyer_tel : '010-1234-5678',
        buyer_addr : '서울특별시 강남구 삼성동',
        buyer_postcode : '123-456',
        m_redirect_url : 'localhost:8080/api/payment/result',
        //app_scheme : 'iamportapp'
    }, function(rsp) {
        if (rsp.success) { // 결제 성공 시: 결제 승인 또는 가상계좌 발급에 성공한 경우
            // jQuery로 HTTP 요청
            jQuery.ajax({
                url: "https://www.myservice.com/payments/complete", // 가맹점 서버
                method: "POST",
                headers: { "Content-Type": "application/json" },
                data: {
                    imp_uid: rsp.imp_uid,
                    merchant_uid: rsp.merchant_uid
                }
            }).done(function (data) {

            })
        } else {
            alert("결제에 실패하였습니다. 에러 내용: " +  rsp.error_msg);
        }

        alert(msg);
    });
</script>

//TODO URLScheme정의하는 부분 추가
</html>