
const findAll = async() => {
    try{
        const option = { method : "GET"} 
        const response = await fetch( "/student" , option )
        const data = await response.json();
        console.log(data);

        const studentTbody = document.querySelector("#studentTbody");
        let html = ``;
        for( let i = 0 ; i < data.length ; i++ ){
            const student = data[i];
            html += ` <tr>
                    <th> ${ student.sno } </th>
                    <th> ${ student.sname } </th>
                    <th> ${ student.skor } </th>
                    <th> ${ student.seng } </th>
                    <th> ${ student.smath } </th>
                    <th> ${ student.sdate } </th>
                    <th> 
                        <button onclick="deleteStudent(${ student.sno })"> 삭제 </button>
                    </th>
                </tr>`
        }
        studentTbody.innerHTML = html;
    }catch(error){
        console.log(error);
    }
}

findAll();

const deleteStudent = async(sno) => {
    try{
        const option = { method : "DELETE" }
        const response = await fetch( `/student?sno=${ sno }` , option )
        const data = await response.json();
        console.log(data);
        if( data ){
            alert("삭제 성공");
            findAll();
        }else{
            alert("삭제 실패");
        }
    }catch(error){
        console.log(error);
    }   
}