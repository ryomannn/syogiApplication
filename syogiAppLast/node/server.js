/**
 * http://usejsdoc.org/
 */
let rooms = [];

//room生成 socket.idを要素に入れるかまだ考慮中
let createRoom = (room_name) => {

    let check = rooms.some(element => element.roomName ===room_name);

    if(check === false){

        //なんかroomID=に識別できるように管理する
        let a = 8;
        // 生成する文字列に含める文字セット
        let c = "abcdefghijklmnopqrstuvwxyz0123456789";
        let cl = c.length;
        let r = "";
        for(var i=0; i<a; i++){
        r += c[Math.floor(Math.random()*cl)];
        }

        room_id = r;

        let room={
            roomId : room_id,
            roomName : room_name,
            count : 1
        };

        rooms.push(room);
        console.log("push完了room");
        console.log(rooms);
        return room;
    }
    else{
        return false;
    }
};

//room名全取得
let getAllRoomName = () => {
    let roomNames =[]

    rooms.forEach((room) => {
        roomNames.push(room.roomName);
    });
    return roomNames;
}

//room名検索
let getRoomId = (room_name) => {
     let check = rooms.some(element => element.roomName ===room_name);

    if(check){
        return (rooms.find((element) => { return element.roomName === room_name;}));
    }
    else{
        return false;
    }
}

//roomId検索
let getRoomName = (room_id) => {
    return (rooms.find((element) => element.roomId === room_id)).roomName;
}
//ルームに何人いるか確認する
let Count = (room_name) => getRoomId(room_name).count;

//ルームの人数をたす
let addCount = (room_name) => getRoomId(room_name).count +=1;

//ルームの人数を減らす
let reduceCount = (room_name) => getRoomId(room_name).count -=1;

//room削除
let deleteRoom = (room_name) => {
    let index = rooms.findIndex((element) =>  element.roomName === room_name);
    rooms.splice(index,1);
}


//user処理-------------------------------------------------------------------------------------------------------------------------------------------------
let users = [];

let createUser = (socket_id ,user_name ) => {

    let user = {
        userId : socket_id,
        userName : user_name,
        roomId : null
    };
    users.push(user);
    return user;
};

//ユーザーsocketIdで検索
let getUserNum = (socket_id) => users.findIndex(element => element.userId ===socket_id);

//ユーザー情報アップデート
let updateUser = (Socket_id, room_id) =>{
    let id =getUserNum(Socket_id);

    if(id ===-1){
        console.log("存在しません");
    }else{
        users[id].roomId = room_id;
        console.log(users);
    }
};

//サーバー処理----------------------------------------------------------------------------------------------------------------------------------
let app = require("express")();
let http = require("http").createServer(app);
let io = require("socket.io")(http);


app.get("/", function (_req, res) {
    res.sendFile(__dirname + "/room.html");
  });

//接続したときの処理
io.on('connection', socket => {

    console.log("サーバーに接続完了");
    console.log(socket.id);
    console.log(users);
    console.log(rooms);

    //userを登録する処理
    socket.on("create-user",(data) =>{

        createUser(socket.id, data.userName);
        io.emit("remove");
        getAllRoomName().forEach((name) => io.emit("show-allRoom",{roomName : name}));
    });

    //room作成処理
    socket.on("create-room",(data) => {

        let NewRoom = createRoom(data.roomName );

        if(NewRoom){
        updateUser(socket.id, getRoomId(data.roomName).roomId);

        socket.join(data.roomName);

        io.to(socket.id).emit("removeName");
        io.to(socket.id).emit("go-chat");
        io.to(socket.id).emit("return-roomName",{roomName : data.roomName});

        //room名表示追加
        io.emit("show-room",{roomName : data.roomName});
        }
        else{
            io.to(socket.id).emit("room-erorr");
         }
    });

    //room参加
    socket.on("join-room", (data) => {
        let exist = getRoomId(data.roomName).roomId;

        if(exist){

            updateUser(socket.id, exist);
            addCount(data.roomName);
            socket.join(data.roomName);
            io.to(socket.id).emit("removeName");
            io.to(socket.id).emit("return-roomName", data);
            io.to(socket.id).emit("go-chat");
        }
        else{
            io.to(socket.id).emit("roomName-erorr");
        }
    });

    //チャット機能
    socket.on("chat", (data) => {

        let roomName = getRoomName(users[getUserNum(socket.id)].roomId);

        let user_name = users[getUserNum(socket.id)].userName;

        io.to(roomName).emit("chat-List", {msg : data.msg , userName : user_name});

    });

    //退室処理
    socket.on("chat-leave", () => {

        let room_name = getRoomName(users[getUserNum(socket.id)].roomId);
        reduceCount(room_name);

        if(Count(room_name) === 0){

            deleteRoom(room_name);
            io.emit("remove");
            getAllRoomName().forEach((name) => io.emit("show-allRoom",{roomName : name}));
        }
        else{}

        socket.leave();
        updateUser(socket.id, null);
    });

    //disconect時
    socket.on('disconnect', () =>{
        console.log("ユーザーが接続を切りました");
    });
});

http.listen(3000, () => {
    console.log('listening on 3000');
});
