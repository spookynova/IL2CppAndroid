#pragma once

#if defined(__clang__)
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wtautological-undefined-compare"
#pragma clang diagnostic ignored "-Wtautological-pointer-compare"
#endif

namespace Unity
{
    struct System_String : il2cppObject
    {
        int32_t m_iLength;       // 0x8
        wchar_t m_wString[1024]; // 0xC
        
        void Clear()
        {
            if (this == nullptr) return;
            
            memset(m_wString, 0, static_cast<size_t>(m_iLength) * 2);
            m_iLength = 0;
        }
        
        wchar_t* ToWideString()
        {
            return m_wString;
        }
        
        int32_t ToLength()
        {
            return m_iLength;
        }
        
        std::string ToString()
        {
            if ((this == nullptr) || (m_wString == nullptr) || (m_iLength == 0))
                return "";
            
            std::string retStr;
            mbstate_t state = mbstate_t();
            char buf[MB_CUR_MAX];
            
            for (int32_t i = 0; i < m_iLength; ++i)
            {
                size_t ret = wcrtomb(buf, m_wString[i], &state);
                if (ret == (size_t)-1)
                {
                    return "";
                }
                retStr.append(buf, ret);
            }
            return retStr;
        }
    };
}

#if defined(__clang__)
#pragma clang diagnostic pop
#endif
