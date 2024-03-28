import axios from "axios";
import React, { useEffect, useState } from "react";
import { useParams } from 'react-router-dom';
import { StreamChat } from "stream-chat";
import {
  Channel,
  ChannelHeader,
  ChannelList,
  Chat,
  MessageInput,
  Thread,
  VirtualizedMessageList,
  Window
} from "stream-chat-react";
import { readLocalStorage } from '../../utils/helper';

import { Spinner } from "@material-tailwind/react";

import "stream-chat-react/dist/css/index.css";
// http://localhost:8080/api/chats/from/3/to/2



// const filters = { type: "messaging", members: { $in: ["little-wood-9"] } };
// // TS tweak No1
const sort = { last_message_at: -1 };

const AdopterChat = () => {

  const userid = readLocalStorage("id")
  const token = readLocalStorage("token")
  const User = JSON.parse(readLocalStorage("User"))

  const [data, setData] = useState(null)
  const [loading, setLoading] = useState(false)

  const { shelterId } = useParams();

  // useEffect(()=>{
  //     // axios.get(`http://localhost:8080/api/chats/from/${userid}/to/${id}`)
  //     // .then()


  // },[])

  // Assuming you fetch these values from the backend somehow
  // const userToken = token; // from backend
  // const apiKey = data.apikey; // from backend
  // const userId = data.userChatId; // from backend
  // const channelId = data.channelId; // from backend
  // TS tweak No2
  const [chatClient, setChatClient] = useState(null);
  const [activeChannel, setActiveChannel] = useState(null);
  const baseurl = `${import.meta.env.VITE_BACKEND_BASE_URL}`;
  // Filter channels by tag
  const filters = {
    type: "messaging",
    // Assuming 'tag' is a custom field you've set on the channel metadata
    petadopter: User.email,
  };

  const initChat = async (data) => {

    const client = StreamChat.getInstance(data.apiKey);

    await client.connectUser(
      {
        id: data.userChatId,
        name: User.firstName,
        image:
          `https://ui-avatars.com/api/?name=${User.firstname}+${User.lastname}`,
      },
      data.token
    );

    setChatClient(client);
    // Join the channel by ID
    const channel = client.channel("messaging", data.channelId);

    // To watch the channel (start receiving events)
    await channel.watch();

    setChatClient(client);
    setActiveChannel(channel);
  };

  useEffect(() => {
    axios.get(`${baseurl}/chats/from/${userid}/to/${shelterId}`, {
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

  if (!chatClient || !activeChannel) {
    return <Spinner color="green" className="flex justify-center align-middle" />;
  }

  return (

    loading
      ?
      <></>
      :
      // <Chat client={chatClient}>
      //   {/* Use filters to only show channels with the specified tag */}
      //   <ChannelList
      //     filters={filters}
      //     sort={sort}
      //     setActiveChannel={setActiveChannel}
      //   />
      //   {activeChannel && (
      //     <Channel channel={activeChannel}>
      //       <Window>
      //         <ChannelHeader />
      //         <VirtualizedMessageList />
      //         <MessageInput />
      //       </Window>
      //       <Thread />
      //     </Channel>
      //   )}
      // </Chat>

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

export default AdopterChat;
