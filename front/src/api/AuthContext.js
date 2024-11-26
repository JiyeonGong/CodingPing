import React, { createContext, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios"

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const navigate = useNavigate();
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [user, setUser] = useState(null);

    // 1. JWT 토큰 만료시 refreshToken으로 재발급 하는 함수
    // 2. JWT 토큰 만료 시간 체크하는 함수

    useEffect(() => {
        const storedUser = localStorage.getItem("user");
        const storedIsLoggedIn = localStorage.getItem("isLoggedIn");

        if (storedUser && storedIsLoggedIn === "true") {
            setUser(JSON.parse(storedUser));
            setIsLoggedIn(true);
        }
    }, []);

    //userData -> { kakaoAccessToken, jwtToken, refreshToken, kakaoId }
    const login = (userData) => {
        setIsLoggedIn(true);
        setUser(userData);

        localStorage.setItem("user", JSON.stringify(userData));
        localStorage.setItem("isLoggedIn", "true");
    };

    const logout = async () => {
        try {
            const userData = JSON.parse(localStorage.getItem("user"));
            const kakaoAccessToken = userData?.kakaoAccessToken;

            if (!kakaoAccessToken) {
                throw new Error("kakaoAccessToken이 없습니다.");
            }

            await axios.post(
                "https://kapi.kakao.com/v1/user/logout",
                {},
                {
                    headers: {
                        "Authorization": `Bearer ${kakaoAccessToken}`
                    },
                }
            );

            setIsLoggedIn(false);
            setUser(null);
            localStorage.clear();
            sessionStorage.clear();
            console.log("로그아웃 성공");

            navigate("/");
        } catch (error) {
            console.error("로그아웃 실패:", error);
        }
    };

    return (
        <AuthContext.Provider value={{ isLoggedIn, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};