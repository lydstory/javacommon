// wingetcpu.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"
#include <Windows.h>
#include <iostream>
using namespace std;
int main()
{
	int nNewUsges = 0;
#define SystemBasicInformation       0
#define SystemPerformanceInformation 2
#define SystemTimeInformation        3
#define Li2Double(x) ((double)((x).HighPart) * 4.294967296E9 + (double)((x).LowPart))
	typedef struct
	{
		DWORD   dwUnknown1;
		ULONG   uKeMaximumIncrement;
		ULONG   uPageSize;
		ULONG   uMmNumberOfPhysicalPages;
		ULONG   uMmLowestPhysicalPage;
		ULONG   uMmHighestPhysicalPage;
		ULONG   uAllocationGranularity;
		PVOID   pLowestUserAddress;
		PVOID   pMmHighestUserAddress;
		ULONG   uKeActiveProcessors;
		BYTE    bKeNumberProcessors;
		BYTE    bUnknown2;
		WORD    wUnknown3;
	} SYSTEM_BASIC_INFORMATION;

	typedef struct
	{
		LARGE_INTEGER   liIdleTime;
		DWORD           dwSpare[76];
	} SYSTEM_PERFORMANCE_INFORMATION;

	typedef struct
	{
		LARGE_INTEGER liKeBootTime;
		LARGE_INTEGER liKeSystemTime;
		LARGE_INTEGER liExpTimeZoneBias;
		ULONG         uCurrentTimeZoneId;
		DWORD         dwReserved;
	} SYSTEM_TIME_INFORMATION;

	static LARGE_INTEGER   m_liOldIdleTime;
	static LARGE_INTEGER   m_liOldSystemTime;

	SYSTEM_PERFORMANCE_INFORMATION SysPerfInfo;
	SYSTEM_TIME_INFORMATION        SysTimeInfo;
	SYSTEM_BASIC_INFORMATION       SysBaseInfo;
	double                         dbIdleTime;
	double                         dbSystemTime;
	typedef LONG(WINAPI *PROCNTQSI)(UINT, PVOID, ULONG, PULONG);
	PROCNTQSI NtQuerySystemInformation;

#ifdef UNICODE
	if (!(NtQuerySystemInformation = (PROCNTQSI)GetProcAddress(GetModuleHandleW(L"ntdll"), "NtQuerySystemInformation")))
		return 0;
#else
	if (!(NtQuerySystemInformation = (PROCNTQSI)GetProcAddress(GetModuleHandle("ntdll"), "NtQuerySystemInformation")))
		return 0;
#endif

	// get number of processors in the system
	if (NtQuerySystemInformation(SystemBasicInformation, &SysBaseInfo, sizeof(SysBaseInfo), NULL) != NO_ERROR)
		return 0;
	if (NtQuerySystemInformation(SystemTimeInformation, &SysTimeInfo, sizeof(SysTimeInfo), 0) != NO_ERROR)
		return 0;

	// get new CPU's idle time
	if (NtQuerySystemInformation(SystemPerformanceInformation, &SysPerfInfo, sizeof(SysPerfInfo), NULL) != NO_ERROR)
	{
		return 0;
	}

	// if it's a first call - skip it
	if (m_liOldIdleTime.QuadPart != 0)
	{
		// CurrentValue = NewValue - OldValue
		dbIdleTime = Li2Double(SysPerfInfo.liIdleTime) - Li2Double(m_liOldIdleTime);
		dbSystemTime = Li2Double(SysTimeInfo.liKeSystemTime) - Li2Double(m_liOldSystemTime);

		// CurrentCpuIdle = IdleTime / SystemTime
		dbIdleTime = dbIdleTime / dbSystemTime;

		// CurrentCpuUsage% = 100 - (CurrentCpuIdle * 100) / NumberOfProcessors
		dbIdleTime = 100.0 - dbIdleTime * 100.0 / (double)SysBaseInfo.bKeNumberProcessors + 0.5;
		nNewUsges = (int)dbIdleTime;
	}

	// store new CPU's idle and system time
	m_liOldIdleTime = SysPerfInfo.liIdleTime;
	m_liOldSystemTime = SysTimeInfo.liKeSystemTime;
	std::cout << nNewUsges << std::endl;
	system("PAUSE");
    return 0;
}

