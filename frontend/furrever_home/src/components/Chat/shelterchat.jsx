import axios from "axios";
import React, { useEffect, useState } from "react";
import { StreamChat } from "stream-chat";
import {
  Channel,
  ChannelHeader,
  ChannelList,
  Chat,
  LoadingIndicator,
  MessageInput,
  Thread,
  VirtualizedMessageList,
  Window,
} from "stream-chat-react";
import { readLocalStorage, saveLocalStorage } from '../../utils/helper';

import "stream-chat-react/dist/css/index.css";




const ShelterChat = () => {
  // TS tweak No2
  const [chatClient, setChatClient] = useState(null);
  const [shelter, setShelter] = useState({})

  const sort = { last_message_at: -1 };

  const userid = readLocalStorage("id")
  const token = readLocalStorage("token")
  const User = JSON.parse(readLocalStorage("User"))
  const baseurl = `${import.meta.env.VITE_BACKEND_BASE_URL}`



  const filters = {
    type: "messaging",
    // Assuming 'tag' is a custom field you've set on the channel metadata
    shelter: User.email
  };

  const initChat = async (data) => {
    const client = StreamChat.getInstance(data.apiKey);

    await client.connectUser(
      {
        id: data.userChatId,
        name: User.name,
        image: `https://ui-avatars.com/api/?name=${User.name}`,
      },
      data.token
    );

    setChatClient(client);
  };


  useEffect(() => {

    axios.get(`${baseurl}/shelter/single/${userid}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {

        saveLocalStorage("User", JSON.stringify(response.data));
        setLoading(true)
      })
      .catch(error => {
        console.log(error);
      })

    axios.get(`${baseurl}/chats/history/${userid}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {

        // setData(response.data)       
        return response.data
      })
      .then(data => {

        initChat(data)
      })
      .catch(error => {
        console.log(error);
      })


    // initChat();
    // Make sure to disconnect the user when the component unmounts
    return () => {
      if (chatClient) {
        chatClient.disconnectUser();
      }
    };
  }, []);

  if (!chatClient) {
    return <LoadingIndicator />;
  }

  return (
    <Chat client={chatClient}>
      <ChannelList filters={filters} sort={sort} />
      <Channel>
        <Window>
          <ChannelHeader />
          <VirtualizedMessageList
            additionalVirtuosoProps={{
              increaseViewportBy: { top: 400, bottom: 200 },
            }}
          />
          <MessageInput />
        </Window>
        <Thread />
      </Channel>
    </Chat>
  );
};

export default ShelterChat;
