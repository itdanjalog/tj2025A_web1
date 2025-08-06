
const save = async() => {
    try{
        const sname = document.querySelector(".sname").value
        const skor = document.querySelector(".skor").value
        const seng = document.querySelector(".seng").value
        const smath = document.querySelector(".smath").value
        const option = {
            method : "POST",
            headers : {
                "Content-Type" : "application/json"
            },
            body : JSON.stringify({
                sname : sname,
                skor : skor,
                seng : seng,
                smath : smath
            })
        }
        const response = await fetch( "/student" , option )
        const data = await response.json(); 
        console.log(data);
        if( data ){
            alert("등록 성공");
            location.href="/student/find.jsp"
        }else{
            alert("등록 실패");
        }
    }catch(error){
        console.log(error);
    }
}
