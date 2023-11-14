import axios from "axios";

const SERVER_IP = `${ process.env.REACT_APP_RESTAPI_SERVER_IP }`;
const SERVER_PORT = `${ process.env.REACT_APP_RESTAPI_SERVER_PORT }`;
const DEFAULT_URL = `http://${ SERVER_IP }:${ SERVER_PORT }/api/v1`;

//`${ DEFAULT_URL }${ url }` : 기본 주소 + 추가 주소
export const request = async (method, url, data) => {
    return await axios({
        method,
        url : `${ DEFAULT_URL }${ url }`,
        data
    })
        .catch(error => console.log(error));
}