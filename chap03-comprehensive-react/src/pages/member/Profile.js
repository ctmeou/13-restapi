import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import {callMemberAPI} from "../../apis/MemberAPICalls";
import ProfileItem from "../../components/items/ProfileItem";

function Profile() {

    const dispatch = useDispatch();
    const { profileInfo } = useSelector(state => state.memberReducer); //프로필에서 구독하고 있다가 필요 시 렌더링

    useEffect(() => {
        dispatch(callMemberAPI());
    }, []);

    return (
        <div className="profile-background-div">
            {
                profileInfo &&
                    <ProfileItem profileInfo={ profileInfo }/> //profileInfo 정보가 있는 경우 ProfileItem을 렌더링하고 필요한 정보를 profileInfo props로 내려준다.
            }
        </div>
    );

}

export default Profile;