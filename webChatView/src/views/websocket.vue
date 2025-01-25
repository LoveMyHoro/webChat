<template>  
  <div class="container">  
    <div class="message-input">  
      <input v-model="inputMessage" type="text" @keyup.enter="sendMessage" placeholder="输入消息..."/>  
      <button @click="sendMessage">发送消息</button>  
    </div>  
    <div class="websocket-control">  
      <button @click="closeWebSocket">关闭连接</button>  
    </div>  
    <div class="message-box" ref="messageBox">  
      <!-- 遍历消息，添加头像和分割的用户名与消息内容 -->  
      <div class="message-item"   
           v-for="(msg, index) in messages"   
           :key="index"   
           :class="{ 'my-message': msg.username === userInfo.username, 'other-message': msg.username !== userInfo.username }">  
        <img v-if="msg.username !== userInfo.username" src="/src/assets/images/avatar.jpg" alt="Avatar" class="avatar">  
        <div class="message-content">  
          <!-- 仅在发送者不是当前用户时显示用户名 -->  
          <strong v-if="msg.username !== userInfo.username">{{ msg.username }}</strong><span v-if="msg.username !== userInfo.username">: </span>{{ msg.content }}  
        </div>  
      </div>  
    </div>  
  </div>  
</template>

<script setup>  
import { ref, onMounted, onBeforeUnmount } from 'vue';  
import { useInfoStore } from '@/stores/userInfo.js';  

// 若需要使用 userInfo 仓库，可以在这里获取实例  
const infoStore = useInfoStore();  
let userInfo = infoStore.userInfo;  
console.log("用户id：" + userInfo.id + ",用户名：" + userInfo.username);  

const websocket = ref(null);  
const inputMessage = ref('');  
const messages = ref([]);  
const messageBox = ref(null);  

// 连接 WebSocket  
const connectWebSocket = () => {  
  if ('WebSocket' in window) {  
    websocket.value = new WebSocket(`ws://localhost:8080/ws/${userInfo.username}`);  
    websocket.value.onerror = onError;  
    websocket.value.onopen = onOpen;  
    websocket.value.onmessage = onMessage;  
    websocket.value.onclose = onClose;  
  } else {  
    alert('浏览器不支持 WebSocket');  
  }  
};  

// 连接发生错误的回调方法  
const onError = () => {  
  messages.value.push({ username: '系统', content: '连接发生错误' });  
};  

// 连接成功建立的回调方法  
const onOpen = () => {  
  messages.value.push({ username: '系统', content: '连接成功' });  
};  

// 接收到消息的回调方法  
const onMessage = (event) => {  
  const [username, content] = event.data.split('.'); // 用 '.' 分割用户名和消息内容  
  messages.value.push({ username, content });  
  // 滚动到消息框底部  
  if (messageBox.value) {  
    messageBox.value.scrollTop = messageBox.value.scrollHeight;  
  }  
};  

// 连接关闭的回调方法  
const onClose = () => {  
  messages.value.push({ username: '系统', content: '连接已关闭' });  
};  

// 发送消息  
const sendMessage = () => {  
  if (websocket.value) {  
    websocket.value.send(`${userInfo.username}.${inputMessage.value}`);  
    inputMessage.value = '';  
  }  
};  

// 关闭连接  
const closeWebSocket = () => {  
  if (websocket.value) {  
    websocket.value.close();  
  }  
};  

onMounted(() => {  
  connectWebSocket();  
});  

onBeforeUnmount(() => {  
  if (websocket.value) {  
    websocket.value.close();  
  }  
});  
</script>  

<style scoped>  
/* 整体容器样式 */  
.container {  
  display: flex;  
  flex-direction: column;  
  align-items: center;  
  gap: 10px;  
  padding: 20px;  
  background-color: #f0f0f0;  
  border-radius: 10px;  
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);  
}  

/* 消息输入区域样式 */  
.message-input {  
  display: flex;  
  gap: 10px;  
  width: 80%;  
}  

/* 输入框样式 */  
input {  
  flex: 1;  
  padding: 8px 12px;  
  border: 1px solid #ccc;  
  border-radius: 5px;  
  font-size: 14px;  
  outline: none;  
  transition: border-color 0.2s ease;  
}  

input:focus {  
  border-color: #007bff;  
}  

/* 按钮通用样式 */  
button {  
  padding: 8px 16px;  
  border: none;  
  border-radius: 5px;  
  background-color: #007bff;  
  color: white;  
  cursor: pointer;  
  font-size: 14px;  
  transition: background-color 0.2s ease;  
}  

button:hover {  
  background-color: #0056b3;  
}  

/* WebSocket 控制按钮区域样式 */  
.websocket-control {  
  margin-top: 10px;  
}  

/* 消息显示框样式 */  
.message-box {  
  width: 80%;  
  min-height: 300px;  
  max-height: 400px;  
  padding: 15px;  
  background-color: white;  
  border: 1px solid #ccc;  
  border-radius: 5px;  
  overflow-y: auto;  
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);  
  display: flex;  
  flex-direction: column;  
  gap: 15px;  
}  

/* 单个消息项样式 */  
.message-item {  
  display: flex;  
  align-items: flex-start;  
  gap: 10px;  
  width: 100%; /* 确保消息项占满整个宽度 */  
}  

/* 当前用户消息样式 */  
.my-message {  
  justify-content: flex-end; /* 把当前用户的消息对齐到右侧 */  
}  

/* 其他用户消息样式 */  
.other-message {  
  justify-content: flex-start; /* 把其他用户的消息对齐到左侧 */  
}  

/* 头像样式 */  
.avatar {  
  width: 40px;  
  height: 40px;  
  border-radius: 50%;  
  object-fit: cover;  
  box-shadow: 0 0 3px rgba(0, 0, 0, 0.3);  
}  

/* 消息内容样式 */  
.message-content {  
  padding: 8px 12px;  
  background-color: #e9f5ff;  
  border-radius: 8px;  
  font-size: 14px;  
  line-height: 1.4;  
  max-width: 70%;  
  word-wrap: break-word;  
}  
</style>