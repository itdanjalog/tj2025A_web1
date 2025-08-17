


const dataAPI1 = async()=>{

    // 부평구 주유소 -> https://www.data.go.kr/data/15102672/fileData.do#tab-layer-openapi
    const URL = "https://api.odcloud.kr/api/15102672/v1/uddi:d26dabc4-e094-463d-a4b1-cab3af66bb6d?page=1&perPage=38&serviceKey=nwPZ%2F9Z3sVtcxGNXxOZfOXwnivybRXYmyoIDyvU%2BVDssxywHNMU2tA55Xa8zvHWK0bninVkiuZAA4550BDqIbQ%3D%3D"
    const option = { method : "GET"}
    const response = await fetch( URL , option );
    const data = await response.json();
    console.log(data);

    const dataTbody = document.querySelector("#dataTbody");

    let html = ``;
    data.data.forEach(element => {
        html += `
        <tr>
            <td>${element.상호}</td>
            <td>${element.업종}</td>
            <td>${element.연번}</td>
            <td>${element.전화번호}</td>
            <td>${element.주소}</td>
        </tr>
        `
    });
    dataTbody.innerHTML = html;
}
dataAPI1();



const dataAPI2 = async()=>{

    // 진위확인 -> https://www.data.go.kr/data/15081808/openapi.do
    const bno = document.querySelector(".bno").value; // 6408101354

    var data = {
    "b_no": [bno] // 사업자번호 "xxxxxxx" 로 조회 시,
   }; 
   
   // nwPZ%2F9Z3sVtcxGNXxOZfOXwnivybRXYmyoIDyvU%2BVDssxywHNMU2tA55Xa8zvHWK0bninVkiuZAA4550BDqIbQ%3D%3D 인코딩 키 

   const URL = "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=nwPZ%2F9Z3sVtcxGNXxOZfOXwnivybRXYmyoIDyvU%2BVDssxywHNMU2tA55Xa8zvHWK0bninVkiuZAA4550BDqIbQ%3D%3D"
   const option = { method : "POST" , headers : {"Content-Type" : "application/json"} , body : JSON.stringify(data)}
   const response = await fetch( URL , option );
   const data2 = await response.json();
   console.log(data2);

   const result = document.querySelector("#result")
   result.innerHTML = data2.data[0]["tax_type"]
}