
console.log( "post.js open");

// [1] 사용자 요청한 URL 에서 cno(카테고리)/page(현페이지번호) 가져오기
// new URL( location.href).searchParams; : 현재 페이지의 URL에서 매개변수(?) 값 반환 
const params = new URL( location.href).searchParams;

const cno = params.get('cno');          console.log( cno );
const page = params.get('page') || 1;           // 만약에 page가 존재하지 않으면 1
const key = params.get('key') || '';            // 만약에 key가 존재하지 않으면 ''
const keyword = params.get('keyword') || '';

// [2] 요청 매개변수를 이용한 fecth 게시물 요청하기 `
const findAll = async ()=>{         console.log('findAll open');
    try{ // 2-1 : try{ }catch(){ } 예외처리 
        // 2-2 fatch 이용한 게시물 요청
        const url = `/post?cno=${ cno }&page=${ page }&key=${ key }&keyword=${ keyword }`;

        const reponse = await fetch( url ); // GET 방식은 option 생략가능 
        const data = await reponse.json();  console.log( data ); // data{} <--> pageDto{}
        // 2-3 fetch 결과를 테이블에 출력하기 
        const postBody = document.querySelector(".postBody");
        let html = '';   // fetch 로 부터 받은 데이터를 html 문자열로 변환 
            data.data.forEach( ( post ) => {  // data.data <---> pagaDto{ data }
                // post <---> postDto{}
                html += `<tr>
                            <td> ${ post.pno } </td>
                            <td> <a href="view.jsp?pno=${ post.pno }">${ post.ptitle } </a> </td>
                            <td> ${ post.mid } </td> 
                            <td> ${ post.pdate } </td>
                            <td> ${ post.pview } </td>
                        </tr>`
            })
        postBody.innerHTML = html;

        // 3. 페이징 버튼 출력 함수 호출 
        viewPageButtons( data );

    }catch( e ){ console.loe(e); }
} //func end 

// [3] 페이징 버튼 출력 함수 , *** findAll 함수 안에서 아래부분 실행 
const viewPageButtons = async( data )=>{
    // 백엔드로 부터 받은 pageDto{} <---> data{}
    let currentPage = parseInt( data.currentPage ); // parseInt( 자료 ) : 자료를 int타입으로 변환 
    let totalPage = data.totalPage;
    let startBtn = data.startBtn;
    let endBtn = data.endBtn;

    // ************ 페이징 처리시 검색 유지 ************** //
    const searchURL = `&key=${ key }&keyword=${ keyword }`;

    const pageBtnBox = document.querySelector('.pageBtnBox');
    let html = ``;
    // ********************************  이전버튼 : 만약에 현재페이지가 1 이하면 1 고정 ************************** //
    html += `<li> <a href="post.jsp?cno=${ cno }&page=${ currentPage <= 1 ? 1 : currentPage - 1 }${ searchURL }"> 이전 </a>  </li>`
    // ********************************  페이지버튼 : startBtn 부터 endBtn 까지 1씩 증가 반복  ************************** //
    for( let i = startBtn ; i <= endBtn ; i++ ){
        html += `<li> 
                    <a href="post.jsp?cno=${ cno }&page=${ i }${ searchURL }" style="${ i == currentPage ? 'color:red' : '' }" >  
                        ${ i } 
                    </a> 
                </li>`   
    }
    // ********************************  다음버튼 : 만약에 다음페이지가 전체페이지수보다 커지면 전체페이지수로 고정 ************************** //
    html += `<li><a href="post.jsp?cno=${ cno }&page=${ currentPage+1 >= totalPage ? totalPage : currentPage + 1 }${ searchURL }"> 다음 </a> </li>`

    pageBtnBox.innerHTML = html;
} // func end 


// [4] 검색 버튼을 클릭했을때
const onSearch = async() =>{
    // 1. 입력받은 key 와 keyword 값 가져오기
    const newKey = document.querySelector('.key').value         // <select>
    const newKeyword = document.querySelector('.keyword').value // <input/>
    
    // 2. 페이지 이동 , 검색하면 주로 1페이지 이동 
    location.href= `post.jsp?cno=${ cno }&page=${ 1 }&key=${ newKey }&keyword=${ newKeyword }`;

}// func end 

findAll(); // JS실행시 최초 1번 실행 
